package com.canerture.satellitesapp.ui.base.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<STATE : State, EFFECT : Effect> : ViewModel() {

    private val initialState: STATE by lazy { setInitialState() }
    private val initialEffect: EFFECT by lazy { setInitialEffect() }

    abstract fun setInitialState(): STATE
    abstract fun setInitialEffect(): EFFECT

    private val _state = mutableStateOf(initialState)
    val state = _state

    private val _effect = mutableStateOf(initialEffect)
    val effect = _effect

    fun setState(state: STATE) {
        _state.value = state
    }

    fun setEffect(effect: EFFECT) {
        _effect.value = effect
    }

    fun getCurrentState() = state.value
}