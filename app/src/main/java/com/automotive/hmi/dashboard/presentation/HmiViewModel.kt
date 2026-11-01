package com.automotive.hmi.dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.automotive.hmi.dashboard.domain.model.VehicleTelemetry
import com.automotive.hmi.dashboard.domain.usecase.GetVehicleTelemetryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HmiViewModel @Inject constructor(
    private val getVehicleTelemetryUseCase: GetVehicleTelemetryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HmiUiState())
    val uiState: StateFlow<HmiUiState> = _uiState.asStateFlow()

    init {
        observeTelemetry()
    }

    private fun observeTelemetry() {
        viewModelScope.launch {
            getVehicleTelemetryUseCase()
                .catch { exception ->
                    _uiState.update {
                        it.copy(errorMessage = "Telemetry Fault: ${exception.localizedMessage}")
                    }
                }
                .collect { telemetry ->
                    processTelemetry(telemetry)
                }
        }
    }

    fun processTelemetry(telemetry: VehicleTelemetry) {
        val safeSpeed = if (telemetry.speedKmh < 0f) 0f else telemetry.speedKmh
        val isInvalid = telemetry.speedKmh < 0f

        _uiState.update { currentState ->
            currentState.copy(
                speedKmh = safeSpeed,
                isMotorOn = telemetry.isMotorOn,
                isKillSwitchActive = telemetry.isKillSwitchActive,
                rideMode = telemetry.mode,
                errorMessage = if (isInvalid) "Invalid speed signal received" else null
            )
        }
    }
}