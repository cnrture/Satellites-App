package com.canerture.satellitesapp.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.canerture.satellitesapp.common.getDataFromJsonString
import com.canerture.satellitesapp.data.model.Position
import com.canerture.satellitesapp.data.model.Satellite
import com.canerture.satellitesapp.data.model.SatelliteDetail
import com.canerture.satellitesapp.domain.usecase.getsatellitedetail.GetSatelliteDetailUseCase
import com.canerture.satellitesapp.domain.usecase.getsatellitedetail.GetSatelliteDetailUseCaseImpl
import com.canerture.satellitesapp.ui.base.viewmodel.BaseViewModel
import com.canerture.satellitesapp.ui.base.viewmodel.Effect
import com.canerture.satellitesapp.ui.base.viewmodel.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getSatelliteDetailUseCase: GetSatelliteDetailUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<DetailState, DetailEffect>() {

    override fun setInitialState() = DetailState(true)
    override fun setInitialEffect() = DetailEffect.Idle

    init {
        getSatelliteDetail(getSatelliteArg())
    }

    private fun getSatelliteArg() =
        getDataFromJsonString<Satellite>((savedStateHandle["satellite"] ?: ""))

    private fun getSatelliteDetail(satellite: Satellite?) = viewModelScope.launch {
        satellite?.let { satellite ->
            getSatelliteDetailUseCase.invoke(satellite.id).collect {
                setState(getCurrentState().copy(isLoading = false))
                when (it) {
                    is GetSatelliteDetailUseCaseImpl.GetSatelliteDetailUseCaseState.Data -> {
                        setState(
                            getCurrentState().copy(
                                satelliteName = satellite.name,
                                satelliteDetail = it.satelliteDetail,
                                position = it.position
                            )
                        )
                    }

                    is GetSatelliteDetailUseCaseImpl.GetSatelliteDetailUseCaseState.Error -> {
                        setEffect(DetailEffect.ShowError(it.message))
                    }

                    GetSatelliteDetailUseCaseImpl.GetSatelliteDetailUseCaseState.EmptyData -> {
                        setEffect(DetailEffect.ShowError("it.message"))
                    }
                }
            }
        } ?: kotlin.run {
            setEffect(DetailEffect.ShowError("it.message"))
        }
    }
}

data class DetailState(
    val isLoading: Boolean = false,
    val satelliteName: String? = null,
    val satelliteDetail: SatelliteDetail? = null,
    val position: Position? = null
) : State

sealed class DetailEffect : Effect {
    object Idle : DetailEffect()
    data class ShowError(val message: String) : DetailEffect()
}