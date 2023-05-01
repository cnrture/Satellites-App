package com.canerture.satellitesapp.ui.screens.satellites

import androidx.lifecycle.viewModelScope
import com.canerture.satellitesapp.data.model.Satellite
import com.canerture.satellitesapp.domain.usecase.GetSatellitesUseCase
import com.canerture.satellitesapp.ui.base.viewmodel.BaseViewModel
import com.canerture.satellitesapp.ui.base.viewmodel.Effect
import com.canerture.satellitesapp.ui.base.viewmodel.Event
import com.canerture.satellitesapp.ui.base.viewmodel.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SatellitesViewModel @Inject constructor(
    private val getSatellitesUseCase: GetSatellitesUseCase
) : BaseViewModel<SatellitesState, SatellitesEffect>() {

    override fun setInitialState() = SatellitesState(true)
    override fun setInitialEffect() = SatellitesEffect.Idle

    init {
        getSatellites()
    }

    fun onQueryTextChange(query: String) = viewModelScope.launch {
        setState(getCurrentState().copy(isLoading = true))
        delay(3000L)
        if (query.length > 2) {
            setState(getCurrentState().copy(satellites = getCurrentState().satellites?.filter {
                it.name.lowercase().contains(query.lowercase())
            }))
            setState(getCurrentState().copy(isLoading = false))
        } else {
            getSatellites()
        }
    }

    private fun getSatellites() = viewModelScope.launch {
        getSatellitesUseCase.invoke().collect {
            setState(getCurrentState().copy(isLoading = false))
            when (it) {
                is GetSatellitesUseCase.GetSatellitesUseCaseState.Satellites -> {
                    setState(getCurrentState().copy(satellites = it.satellites))
                }

                is GetSatellitesUseCase.GetSatellitesUseCaseState.Error -> {
                    setEffect(SatellitesEffect.ShowError(it.message))
                }

                GetSatellitesUseCase.GetSatellitesUseCaseState.EmptyList -> {
                    setEffect(SatellitesEffect.ShowError("it.message"))
                }
            }
        }
    }
}

data class SatellitesState(
    val isLoading: Boolean = false,
    val satellites: List<Satellite>? = null
) : State

sealed class SatellitesEvent : Event {
    object Idle : SatellitesEvent()
    data class QueryTextChanged(val query: String) : SatellitesEvent()
    data class SatelliteClicked(val query: String) : SatellitesEvent()
}

sealed class SatellitesEffect : Effect {
    object Idle : SatellitesEffect()
    data class ShowError(val message: String) : SatellitesEffect()
}