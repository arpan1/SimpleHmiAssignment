package com.automotive.hmi.dashboard.domain.repository

import com.automotive.hmi.dashboard.domain.model.VehicleTelemetry
import kotlinx.coroutines.flow.Flow

interface VehicleRepository {
    fun getVehicleTelemetryStream(): Flow<VehicleTelemetry>
}