package com.canerture.satellitesapp.detail

import androidx.lifecycle.SavedStateHandle
import com.canerture.satellitesapp.common.FlowTestUtils
import com.canerture.satellitesapp.detail.DetailTestDataFactory.position
import com.canerture.satellitesapp.detail.DetailTestDataFactory.satellite
import com.canerture.satellitesapp.detail.DetailTestDataFactory.satelliteDetail
import com.canerture.satellitesapp.detail.DetailTestDataFactory.satelliteId
import com.canerture.satellitesapp.domain.usecase.getsatellitedetail.GetSatelliteDetailUseCase
import com.canerture.satellitesapp.domain.usecase.getsatellitedetail.GetSatelliteDetailUseCaseImpl
import com.canerture.satellitesapp.infrastructure.StringResourceProvider
import com.canerture.satellitesapp.ui.screens.detail.DetailEffect
import com.canerture.satellitesapp.ui.screens.detail.DetailState
import com.canerture.satellitesapp.ui.screens.detail.DetailViewModel
import io.mockk.clearAllMocks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel

    @Mock
    private lateinit var getSatelliteDetailUseCase: GetSatelliteDetailUseCase

    @Mock
    private lateinit var savedStateHandle: SavedStateHandle

    @Mock
    private lateinit var stringResourceProvider: StringResourceProvider

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = DetailViewModel(
            getSatelliteDetailUseCase,
            savedStateHandle,
            stringResourceProvider
        )
    }

    @Test
    fun whenGetSatelliteDetailUseCaseStateIsData_detailDataStateReturned() {
        whenever(getSatelliteDetailUseCase.invoke(satelliteId)).thenReturn(
            listOf(
                GetSatelliteDetailUseCaseImpl.GetSatelliteDetailUseCaseState.Data(
                    satelliteDetail,
                    position
                )
            ).asFlow()
        )
        FlowTestUtils.testStateAfterUseCaseState(
            flowToBeCollected = viewModel.state,
            launchService = { viewModel.getSatelliteDetail(satellite) },
            assertEquals = { actualState ->
                Pair(
                    DetailState.SatelliteDetailData(satellite.name, satelliteDetail, position),
                    actualState
                )
            }
        )
    }

    @Test
    fun whenGetSatelliteDetailUseCaseStateIsError_detailShowErrorEffectReturned() {
        whenever(getSatelliteDetailUseCase.invoke(satelliteId)).thenReturn(
            listOf(GetSatelliteDetailUseCaseImpl.GetSatelliteDetailUseCaseState.Error("")).asFlow()
        )
        FlowTestUtils.testEffectAfterUseCaseState(
            flowToBeCollected = viewModel.effect,
            launchService = { viewModel.getSatelliteDetail(satellite) },
            assert = { actualEffect ->
                actualEffect is DetailEffect.ShowError
            }
        )
    }

    @Test
    fun whenGetSatelliteDetailUseCaseStateIsEmptyData_detailEmptyDataStateReturned() {
        whenever(getSatelliteDetailUseCase.invoke(satelliteId)).thenReturn(
            listOf(GetSatelliteDetailUseCaseImpl.GetSatelliteDetailUseCaseState.EmptyData).asFlow()
        )
        FlowTestUtils.testStateAfterUseCaseState(
            flowToBeCollected = viewModel.state,
            launchService = { viewModel.getSatelliteDetail(satellite) },
            assertEquals = { actualState ->
                Pair(DetailState.EmptyData, actualState)
            }
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }
}