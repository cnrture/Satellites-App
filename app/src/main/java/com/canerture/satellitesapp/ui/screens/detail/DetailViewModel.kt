package com.canerture.satellitesapp.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.canerture.satellitesapp.R
import com.canerture.satellitesapp.common.Constants.Key
import com.canerture.satellitesapp.common.getDataFromJsonString
import com.canerture.satellitesapp.data.model.Position
import com.canerture.satellitesapp.data.model.Satellite
import com.canerture.satellitesapp.data.model.SatelliteDetail
import com.canerture.satellitesapp.domain.usecase.getsatellitedetail.GetSatelliteDetailUseCase
import com.canerture.satellitesapp.domain.usecase.getsatellitedetail.GetSatelliteDetailUseCaseImpl
import com.canerture.satellitesapp.infrastructure.StringResourceProvider
import com.canerture.satellitesapp.ui.base.viewmodel.BaseViewModel
import com.canerture.satellitesapp.ui.base.viewmodel.IEffect
import com.canerture.satellitesapp.ui.base.viewmodel.IState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getSatelliteDetailUseCase: GetSatelliteDetailUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val stringResourceProvider: StringResourceProvider
) : BaseViewModel<DetailState, DetailEffect>() {

    override fun setInitialState() = DetailState(true)

    init {
        getSatelliteDetail(getSatelliteArg())
    }

    private fun getSatelliteArg() =
        getDataFromJsonString<Satellite>((savedStateHandle[Key.SATELLITE] ?: ""))

    private fun getSatelliteDetail(satellite: Satellite?) = viewModelScope.launch {
        satellite?.let { satellite ->
            getSatelliteDetailUseCase.invoke(satellite.id).collect {
                when (it) {
                    is GetSatelliteDetailUseCaseImpl.GetSatelliteDetailUseCaseState.Data -> {
                        setState {
                            getCurrentState().copy(
                                isLoading = false,
                                satelliteName = satellite.name,
                                satelliteDetail = it.satelliteDetail,
                                position = it.position
                            )
                        }
                    }

                    is GetSatelliteDetailUseCaseImpl.GetSatelliteDetailUseCaseState.Error -> {
                        setEffect(DetailEffect.ShowError(it.message))
                        setState { getCurrentState().copy(isLoading = false) }
                    }

                    GetSatelliteDetailUseCaseImpl.GetSatelliteDetailUseCaseState.EmptyData -> {
                        setState {
                            getCurrentState().copy(
                                isLoading = false,
                                satelliteName = null,
                                satelliteDetail = null,
                                position = null
                            )
                        }
                    }
                }
            }
        } ?: kotlin.run {
            setEffect(DetailEffect.ShowError(stringResourceProvider.getString(R.string.something_went_wrong)))
        }
    }
}

data class DetailState(
    val isLoading: Boolean = false,
    val satelliteName: String? = null,
    val satelliteDetail: SatelliteDetail? = null,
    val position: Position? = null
) : IState

sealed class DetailEffect : IEffect {
    object Idle : DetailEffect()
    data class ShowError(val message: String) : DetailEffect()
}