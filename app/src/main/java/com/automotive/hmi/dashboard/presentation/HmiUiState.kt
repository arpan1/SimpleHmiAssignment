package com.automotive.hmi.dashboard.presentation

import com.automotive.hmi.dashboard.domain.model.RideMode

data class HmiUiState(
    val speedKmh: Float = 0f,
    val isMotorOn: Boolean = false,
    val isKillSwitchActive: Boolean = false,
    val rideMode: RideMode = RideMode.PARKED,
    val errorMessage: String? = null
)