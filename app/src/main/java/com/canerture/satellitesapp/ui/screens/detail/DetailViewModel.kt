package com.canerture.satellitesapp.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.canerture.satellitesapp.data.model.Satellite
import com.canerture.satellitesapp.data.model.SatelliteDetail
import com.canerture.satellitesapp.domain.usecase.GetSatelliteDetailUseCase
import com.canerture.satellitesapp.ui.base.viewmodel.BaseViewModel
import com.canerture.satellitesapp.ui.base.viewmodel.Effect
import com.canerture.satellitesapp.ui.base.viewmodel.Event
import com.canerture.satellitesapp.ui.base.viewmodel.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getSatelliteDetailUseCase: GetSatelliteDetailUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<DetailState, DetailEvent, DetailEffect>() {

    override fun setInitialState() = DetailState(true)
    override fun setInitialEffect() = DetailEffect.Idle
    override fun setInitialEvent() = DetailEvent.Idle

    override fun handleEvents(event: DetailEvent) {
        when (event) {
            DetailEvent.Idle -> Unit
        }
    }

    init {
        getSatelliteDetail(getSatelliteArg())
    }

    private fun getSatelliteArg() = Satellite.fromJson(savedStateHandle["satellite"] ?: "")

    private fun getSatelliteDetail(satellite: Satellite?) = viewModelScope.launch {
        satellite?.let { satellite ->
            getSatelliteDetailUseCase.invoke(satellite.id).collect {
                when (it) {
                    is GetSatelliteDetailUseCase.GetSatelliteDetailUseCaseState.SatelliteDetailItem -> {
                        setState(
                            getCurrentState().copy(
                                satelliteName = satellite.name,
                                satelliteDetail = it.satelliteDetail
                            )
                        )
                    }

                    is GetSatelliteDetailUseCase.GetSatelliteDetailUseCaseState.Error -> {
                        setEffect(DetailEffect.ShowError(it.message))
                    }

                    GetSatelliteDetailUseCase.GetSatelliteDetailUseCaseState.EmptySatellite -> {
                        setEffect(DetailEffect.ShowError("it.message"))
                    }
                }
            }
            setState(getCurrentState().copy(isLoading = false))
        } ?: kotlin.run {
            setEffect(DetailEffect.ShowError("it.message"))
        }
    }
}

data class DetailState(
    val isLoading: Boolean = false,
    val satelliteName: String? = null,
    val satelliteDetail: SatelliteDetail? = null
) : State

sealed class DetailEvent : Event {
    object Idle : DetailEvent()
}

sealed class DetailEffect : Effect {
    object Idle : DetailEffect()
    data class ShowError(val message: String) : DetailEffect()
}