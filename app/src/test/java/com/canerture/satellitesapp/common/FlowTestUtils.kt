package com.canerture.satellitesapp.common

import app.cash.turbine.test
import com.canerture.satellitesapp.ui.base.viewmodel.IEffect
import com.canerture.satellitesapp.ui.base.viewmodel.IState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert

@OptIn(ExperimentalCoroutinesApi::class)
object FlowTestUtils {

    fun testStateAfterUseCaseState(
        flowToBeCollected: Flow<IState>,
        launchService: suspend Job.() -> Unit,
        extras: (() -> Unit)? = null,
        assertEquals: (IState) -> Pair<IState, IState>
    ) = runTest {
        launch {
            flowToBeCollected.test {
                val states = assertEquals.invoke(awaitItem())
                Assert.assertEquals(states.first, states.second)
                cancelAndConsumeRemainingEvents()
            }
        }.apply {
            extras?.invoke()
            launchService.invoke(this)
            join()
            cancel()
        }
    }

    fun testEffectAfterUseCaseState(
        flowToBeCollected: Flow<IEffect>,
        launchService: suspend Job.() -> Unit,
        extras: (() -> Unit)? = null,
        assert: (IEffect) -> Boolean
    ) = runTest {
        launch {
            flowToBeCollected.test {
                assert(assert.invoke(awaitItem()))
                cancelAndConsumeRemainingEvents()
            }
        }.apply {
            extras?.invoke()
            launchService.invoke(this)
            join()
            cancel()
        }
    }
}