package com.canerture.satellitesapp.ui.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : IState, Effect : IEffect> : ViewModel() {

    private val initialState: State by lazy { setInitialState() }

    abstract fun setInitialState(): State

    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state

    private val _effect: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effect: SharedFlow<Effect> = _effect.asSharedFlow()

    fun setState(reduce: State.() -> State) {
        _state.update { getCurrentState().reduce() }
    }

    fun setEffect(effect: Effect) {
        viewModelScope.launch { _effect.emit(effect) }
    }

    fun getCurrentState() = state.value
}