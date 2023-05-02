package com.canerture.satellitesapp.ui.screens.satellites

import androidx.lifecycle.viewModelScope
import com.canerture.satellitesapp.data.model.Satellite
import com.canerture.satellitesapp.domain.usecase.getsatellites.GetSatellitesUseCase
import com.canerture.satellitesapp.domain.usecase.getsatellites.GetSatellitesUseCaseImpl
import com.canerture.satellitesapp.domain.usecase.searchsatellitesusecase.SearchSatellitesUseCase
import com.canerture.satellitesapp.domain.usecase.searchsatellitesusecase.SearchSatellitesUseCaseImpl
import com.canerture.satellitesapp.infrastructure.StringResourceProvider
import com.canerture.satellitesapp.ui.base.viewmodel.BaseViewModel
import com.canerture.satellitesapp.ui.base.viewmodel.IEffect
import com.canerture.satellitesapp.ui.base.viewmodel.IState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SatellitesViewModel @Inject constructor(
    private val getSatellitesUseCase: GetSatellitesUseCase,
    private val searchSatellitesUseCase: SearchSatellitesUseCase,
    private val stringResourceProvider: StringResourceProvider
) : BaseViewModel<SatellitesState, SatellitesEffect>() {

    override fun setInitialState() = SatellitesState(true)

    init {
        getSatellites()
    }

    fun onQueryTextChange(query: String) = viewModelScope.launch {
        if (query.length > 2) {
            setState { getCurrentState().copy(isLoading = true) }
            delay(500)
            searchSatellites(query)
        } else {
            setState { getCurrentState().copy(isLoading = true) }
            getSatellites()
        }
    }

    private fun getSatellites() {
        viewModelScope.launch {
            getSatellitesUseCase.invoke().collect {
                setState { getCurrentState().copy(isLoading = false) }
                when (it) {
                    is GetSatellitesUseCaseImpl.GetSatellitesUseCaseState.Data -> {
                        setState { getCurrentState().copy(satellites = it.satellites) }
                    }

                    is GetSatellitesUseCaseImpl.GetSatellitesUseCaseState.Error -> {
                        setEffect(SatellitesEffect.ShowError(it.message))
                    }

                    GetSatellitesUseCaseImpl.GetSatellitesUseCaseState.EmptyData -> {
                        setState { getCurrentState().copy(satellites = null) }
                    }
                }
            }
        }
    }

    private fun searchSatellites(query: String) {
        viewModelScope.launch {
            searchSatellitesUseCase.invoke(query).collect {
                setState { getCurrentState().copy(isLoading = false) }
                when (it) {
                    is SearchSatellitesUseCaseImpl.SearchSatellitesUseCaseState.Data -> {
                        setState { getCurrentState().copy(satellites = it.satellites) }
                    }

                    is SearchSatellitesUseCaseImpl.SearchSatellitesUseCaseState.Error -> {
                        setEffect(SatellitesEffect.ShowError(it.message))
                    }

                    SearchSatellitesUseCaseImpl.SearchSatellitesUseCaseState.EmptyData -> {
                        setState { getCurrentState().copy(satellites = null) }
                    }
                }
            }
        }
    }
}

data class SatellitesState(
    val isLoading: Boolean = false,
    val satellites: List<Satellite>? = null
) : IState

sealed class SatellitesEffect : IEffect {
    object Idle : SatellitesEffect()
    data class ShowError(val message: String) : SatellitesEffect()
}