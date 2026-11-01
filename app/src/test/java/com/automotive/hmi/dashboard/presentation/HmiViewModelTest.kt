package com.automotive.hmi.dashboard.presentation



import com.automotive.hmi.dashboard.domain.model.RideMode
import com.automotive.hmi.dashboard.domain.model.VehicleTelemetry
import com.automotive.hmi.dashboard.domain.usecase.GetVehicleTelemetryUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HmiViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val useCase: GetVehicleTelemetryUseCase = mockk()
    private lateinit var viewModel: HmiViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when telemetry stream emits normal data, uiState updates correctly`() = runTest {

        val mockTelemetry = VehicleTelemetry(
            speedKmh = 45.5f,
            isMotorOn = true,
            isKillSwitchActive = false,
            mode = RideMode.RIDING
        )
        coEvery { useCase.invoke() } returns flowOf(mockTelemetry)


        viewModel = HmiViewModel(useCase)
        testDispatcher.scheduler.advanceUntilIdle()


        val currentState = viewModel.uiState.value
        assertEquals(45.5f, currentState.speedKmh)
        assertTrue(currentState.isMotorOn)
        assertEquals(false, currentState.isKillSwitchActive)
        assertEquals(RideMode.RIDING, currentState.rideMode)
        assertEquals(null, currentState.errorMessage)
    }

    @Test
    fun `when negative speed signal is received, speed is clamped to zero and error is shown`() = runTest {

        val malformedTelemetry = VehicleTelemetry(
            speedKmh = -10.0f,
            isMotorOn = true,
            isKillSwitchActive = false,
            mode = RideMode.ACCELERATING
        )
        coEvery { useCase.invoke() } returns flowOf(malformedTelemetry)


        viewModel = HmiViewModel(useCase)
        testDispatcher.scheduler.advanceUntilIdle()


        val currentState = viewModel.uiState.value
        assertEquals(0.0f, currentState.speedKmh) // Clamped securely
        assertEquals("Invalid speed signal received", currentState.errorMessage)
    }

    @Test
    fun `when repository throws exception, uiState captures signal fault error`() = runTest {

        coEvery { useCase.invoke() } returns flow {
            throw RuntimeException("CAN bus timeout")
        }


        viewModel = HmiViewModel(useCase)
        testDispatcher.scheduler.advanceUntilIdle()


        val currentState = viewModel.uiState.value
        assertTrue(currentState.errorMessage?.contains("CAN bus timeout") == true)
    }
}