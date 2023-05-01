package com.canerture.satellitesapp.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.canerture.satellitesapp.data.model.SatelliteDetail
import com.canerture.satellitesapp.ui.base.viewmodel.BaseViewModel
import com.canerture.satellitesapp.ui.base.viewmodel.Effect
import com.canerture.satellitesapp.ui.base.viewmodel.Event
import com.canerture.satellitesapp.ui.base.viewmodel.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
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
        getSatelliteDetail(getSatelliteId()?.toInt() ?: 1)
    }

    private fun getSatelliteId() = savedStateHandle.get<String>("satelliteId")

    private fun getSatelliteDetail(satelliteId: Int) = viewModelScope.launch {

    }
}

data class DetailState(
    val isLoading: Boolean = false,
    val satelliteDetail: SatelliteDetail? = null
) : State

sealed class DetailEvent : Event {
    object Idle : DetailEvent()
}

sealed class DetailEffect : Effect {
    object Idle : DetailEffect()
    data class ShowError(val message: String) : DetailEffect()
}