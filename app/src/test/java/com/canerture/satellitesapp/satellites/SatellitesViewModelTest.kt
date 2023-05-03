package com.canerture.satellitesapp.satellites

import com.canerture.satellitesapp.common.FlowTestUtils
import com.canerture.satellitesapp.domain.usecase.getsatellites.GetSatellitesUseCase
import com.canerture.satellitesapp.domain.usecase.getsatellites.GetSatellitesUseCaseImpl
import com.canerture.satellitesapp.domain.usecase.searchsatellitesusecase.SearchSatellitesUseCase
import com.canerture.satellitesapp.domain.usecase.searchsatellitesusecase.SearchSatellitesUseCaseImpl
import com.canerture.satellitesapp.infrastructure.StringResourceProvider
import com.canerture.satellitesapp.satellites.SatellitesTestDataFactory.query
import com.canerture.satellitesapp.satellites.SatellitesTestDataFactory.satelliteList
import com.canerture.satellitesapp.satellites.SatellitesTestDataFactory.searchedSatelliteList
import com.canerture.satellitesapp.ui.screens.satellites.SatellitesEffect
import com.canerture.satellitesapp.ui.screens.satellites.SatellitesState
import com.canerture.satellitesapp.ui.screens.satellites.SatellitesViewModel
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
class SatellitesViewModelTest {

    private lateinit var viewModel: SatellitesViewModel

    @Mock
    private lateinit var getSatellitesUseCase: GetSatellitesUseCase

    @Mock
    private lateinit var searchSatellitesUseCase: SearchSatellitesUseCase

    @Mock
    private lateinit var stringResourceProvider: StringResourceProvider

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = SatellitesViewModel(
            getSatellitesUseCase,
            searchSatellitesUseCase,
            stringResourceProvider
        )
    }

    @Test
    fun whenGetSatelliteUseCaseStateIsData_satellitesDataStateReturned() {
        whenever(getSatellitesUseCase.invoke()).thenReturn(
            listOf(GetSatellitesUseCaseImpl.GetSatellitesUseCaseState.Data(satelliteList)).asFlow()
        )
        FlowTestUtils.testStateAfterUseCaseState(
            flowToBeCollected = viewModel.state,
            launchService = { viewModel.getSatellites() },
            assertEquals = { actualState ->
                Pair(SatellitesState.SatellitesData(satelliteList), actualState)
            }
        )
    }

    @Test
    fun whenGetSatelliteUseCaseStateIsError_satellitesShowErrorEffectReturned() {
        whenever(getSatellitesUseCase.invoke()).thenReturn(
            listOf(GetSatellitesUseCaseImpl.GetSatellitesUseCaseState.Error("")).asFlow()
        )
        FlowTestUtils.testEffectAfterUseCaseState(
            flowToBeCollected = viewModel.effect,
            launchService = { viewModel.getSatellites() },
            assert = { actualEffect ->
                actualEffect is SatellitesEffect.ShowError
            }
        )
    }

    @Test
    fun whenGetSatelliteUseCaseStateIsEmptyData_satellitesEmptyDataStateReturned() {
        whenever(getSatellitesUseCase.invoke()).thenReturn(
            listOf(GetSatellitesUseCaseImpl.GetSatellitesUseCaseState.EmptyData).asFlow()
        )
        FlowTestUtils.testStateAfterUseCaseState(
            flowToBeCollected = viewModel.state,
            launchService = { viewModel.getSatellites() },
            assertEquals = { actualState ->
                Pair(SatellitesState.EmptyData, actualState)
            }
        )
    }

    @Test
    fun whenSearchSatellitesUseCaseStateIsData_satellitesDataStateReturned() {
        whenever(searchSatellitesUseCase.invoke(query)).thenReturn(
            listOf(
                SearchSatellitesUseCaseImpl.SearchSatellitesUseCaseState.Data(
                    searchedSatelliteList
                )
            ).asFlow()
        )
        FlowTestUtils.testStateAfterUseCaseState(
            flowToBeCollected = viewModel.state,
            launchService = { viewModel.searchSatellites(query) },
            assertEquals = { actualState ->
                Pair(SatellitesState.SatellitesData(searchedSatelliteList), actualState)
            }
        )
    }

    @Test
    fun whenSearchSatellitesUseCaseStateIsError_satellitesShowErrorEffectReturned() {
        whenever(searchSatellitesUseCase.invoke(query)).thenReturn(
            listOf(SearchSatellitesUseCaseImpl.SearchSatellitesUseCaseState.Error("")).asFlow()
        )
        FlowTestUtils.testEffectAfterUseCaseState(
            flowToBeCollected = viewModel.effect,
            launchService = { viewModel.searchSatellites(query) },
            assert = { actualEffect ->
                actualEffect is SatellitesEffect.ShowError
            }
        )
    }

    @Test
    fun whenSearchSatellitesUseCaseIsEmptyData_satellitesEmptyDataStateReturned() {
        whenever(searchSatellitesUseCase.invoke(query)).thenReturn(
            listOf(SearchSatellitesUseCaseImpl.SearchSatellitesUseCaseState.EmptyData).asFlow()
        )
        FlowTestUtils.testStateAfterUseCaseState(
            flowToBeCollected = viewModel.state,
            launchService = { viewModel.searchSatellites(query) },
            assertEquals = { actualState ->
                Pair(SatellitesState.EmptyData, actualState)
            }
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }
}