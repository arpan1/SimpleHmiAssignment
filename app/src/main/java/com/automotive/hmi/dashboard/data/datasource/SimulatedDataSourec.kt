package com.automotive.hmi.dashboard.data.datasource

import com.automotive.hmi.dashboard.domain.model.RideMode
import com.automotive.hmi.dashboard.domain.model.VehicleTelemetry
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SimulatedVehicleDataSource @Inject constructor() {

    fun observeTelemetry(): Flow<VehicleTelemetry> = flow {
        val sequence = listOf(
            VehicleTelemetry(0f, isMotorOn = false, isKillSwitchActive = false, mode = RideMode.PARKED),
            VehicleTelemetry(0f, isMotorOn = true, isKillSwitchActive = false, mode = RideMode.READY),
            VehicleTelemetry(25f, isMotorOn = true, isKillSwitchActive = false, mode = RideMode.RIDING),
            VehicleTelemetry(50f, isMotorOn = true, isKillSwitchActive = false, mode = RideMode.ACCELERATING),
            VehicleTelemetry(0f, isMotorOn = false, isKillSwitchActive = true, mode = RideMode.MOTOR_DISABLED)
        )

        while (true) {
            for (telemetry in sequence) {
                emit(telemetry)
                delay(3000)
            }
        }
    }
}