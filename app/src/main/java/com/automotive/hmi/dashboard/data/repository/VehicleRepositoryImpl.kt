package com.automotive.hmi.dashboard.data.repository

import com.automotive.hmi.dashboard.data.datasource.SimulatedVehicleDataSource
import com.automotive.hmi.dashboard.domain.model.VehicleTelemetry
import com.automotive.hmi.dashboard.domain.repository.VehicleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VehicleRepositoryImpl @Inject constructor(
    private val dataSource: SimulatedVehicleDataSource
) : VehicleRepository {

    override fun getVehicleTelemetryStream(): Flow<VehicleTelemetry> {
        return dataSource.observeTelemetry()
    }
}