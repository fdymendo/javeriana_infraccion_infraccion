package com.fdymendo.javeriana.infracciones.repository

import com.fdymendo.javeriana.infracciones.entity.InfractionEntity
import com.fdymendo.javeriana.infracciones.entity.VehicleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface InfractionRepository: JpaRepository<InfractionEntity, String>{

    fun getByVehicle(vehicleEntity: VehicleEntity): Collection<InfractionEntity>

}