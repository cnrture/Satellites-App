package com.canerture.satellitesapp.ui.satellites

import androidx.lifecycle.viewModelScope
import com.canerture.satellitesapp.data.model.Satellite
import com.canerture.satellitesapp.domain.usecase.GetSatellitesUseCase
import com.canerture.satellitesapp.ui.base.viewmodel.BaseViewModel
import com.canerture.satellitesapp.ui.base.viewmodel.Effect
import com.canerture.satellitesapp.ui.base.viewmodel.Event
import com.canerture.satellitesapp.ui.base.viewmodel.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SatellitesViewModel @Inject constructor(
    private val getSatellitesUseCase: GetSatellitesUseCase
) : BaseViewModel<SatellitesState, SatellitesEvent, SatellitesEffect>() {

    override fun setInitialState() = SatellitesState(true)
    override fun setInitialEffect() = SatellitesEffect.Idle
    override fun setInitialEvent() = SatellitesEvent.Idle

    override fun handleEvents(event: SatellitesEvent) {
        when (event) {
            SatellitesEvent.Idle -> Unit
            is SatellitesEvent.SatelliteSearched -> {

            }

            is SatellitesEvent.SatelliteClicked -> {

            }
        }
    }

    init {
        getSatellites()
    }

    private fun getSatellites() = viewModelScope.launch {
        getSatellitesUseCase.invoke().collect {
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
            setState(getCurrentState().copy(isLoading = false))
        }
    }
}

data class SatellitesState(
    val isLoading: Boolean = false,
    val satellites: List<Satellite>? = null
) : State

sealed class SatellitesEvent : Event {
    object Idle : SatellitesEvent()
    data class SatelliteSearched(val query: String) : SatellitesEvent()
    data class SatelliteClicked(val query: String) : SatellitesEvent()
}

sealed class SatellitesEffect : Effect {
    object Idle : SatellitesEffect()
    data class ShowError(val message: String) : SatellitesEffect()
}