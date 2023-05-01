package com.canerture.satellitesapp.ui.base.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE : State, EVENT : Event, EFFECT : Effect> : ViewModel() {

    private val initialState: STATE by lazy { setInitialState() }
    private val initialEffect: EFFECT by lazy { setInitialEffect() }
    private val initialEvent: EVENT by lazy { setInitialEvent() }

    abstract fun setInitialState(): STATE
    abstract fun setInitialEffect(): EFFECT
    abstract fun setInitialEvent(): EVENT

    private val _state = mutableStateOf(initialState)
    val state = _state

    private val _event = mutableStateOf(initialEvent)
    val event = _event

    private val _effect = mutableStateOf(initialEffect)
    val effect = _effect

    init {
        subscribeToEvents()
    }

    abstract fun handleEvents(event: EVENT)

    private fun subscribeToEvents() {
        handleEvents(_event.value)
    }

    fun setState(state: STATE) {
        _state.value = state
    }

    fun setEvent(event: EVENT) {
        _event.value = event
    }

    fun setEffect(effect: EFFECT) {
        _effect.value = effect
    }

    fun getCurrentState() = state.value
}