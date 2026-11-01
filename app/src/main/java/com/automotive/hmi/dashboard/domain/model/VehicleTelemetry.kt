package com.automotive.hmi.dashboard.domain.model

data class VehicleTelemetry(val speedKmh: Float,
                            val isMotorOn: Boolean,
                            val isKillSwitchActive: Boolean,
                            val mode: RideMode)
