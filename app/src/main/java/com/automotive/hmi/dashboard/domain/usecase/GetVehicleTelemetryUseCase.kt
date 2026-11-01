package com.automotive.hmi.dashboard.domain.usecase

import com.automotive.hmi.dashboard.domain.model.VehicleTelemetry
import com.automotive.hmi.dashboard.domain.repository.VehicleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVehicleTelemetryUseCase @Inject constructor(
    private val repository: VehicleRepository
) {
    operator fun invoke(): Flow<VehicleTelemetry> {
        return repository.getVehicleTelemetryStream()
    }
}