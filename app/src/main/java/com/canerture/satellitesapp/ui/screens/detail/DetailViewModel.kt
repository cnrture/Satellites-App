package com.canerture.satellitesapp.ui.screens.detail

import androidx.annotation.VisibleForTesting
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

    override fun setInitialState() = DetailState.Loading(true)

    init {
        getSatelliteDetail(getDetailArg())
    }

    private fun getDetailArg() =
        getDataFromJsonString<Satellite>((savedStateHandle[Key.SATELLITE] ?: ""))

    @VisibleForTesting
    fun getSatelliteDetail(satellite: Satellite?) = viewModelScope.launch {
        setState(DetailState.Loading(false))
        satellite?.let { satellite ->
            getSatelliteDetailUseCase.invoke(satellite.id).collect {
                when (it) {
                    is GetSatelliteDetailUseCaseImpl.GetSatelliteDetailUseCaseState.Data -> {
                        setState(
                            DetailState.SatelliteDetailData(
                                satellite.name,
                                it.satelliteDetail,
                                it.position
                            )
                        )
                    }

                    is GetSatelliteDetailUseCaseImpl.GetSatelliteDetailUseCaseState.Error -> {
                        setEffect { DetailEffect.ShowError(it.message) }
                    }

                    GetSatelliteDetailUseCaseImpl.GetSatelliteDetailUseCaseState.EmptyData -> {
                        setState(DetailState.EmptyData)
                    }
                }
            }
        } ?: kotlin.run {
            setEffect { DetailEffect.ShowError(stringResourceProvider.getString(R.string.something_went_wrong)) }
        }
    }
}

sealed interface DetailState : IState {
    data class Loading(val isLoading: Boolean) : DetailState
    data class SatelliteDetailData(
        val satelliteName: String,
        val satelliteDetail: SatelliteDetail,
        val position: Position
    ) : DetailState

    object EmptyData : DetailState
}

sealed interface DetailEffect : IEffect {
    object Idle : DetailEffect
    data class ShowError(val message: String) : DetailEffect
}