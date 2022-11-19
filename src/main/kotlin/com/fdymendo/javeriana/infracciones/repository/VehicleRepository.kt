package com.fdymendo.javeriana.infracciones.repository

import com.fdymendo.javeriana.infracciones.entity.VehicleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface VehicleRepository : JpaRepository<VehicleEntity, String> {
    fun getByPlate(plate: String): VehicleEntity?
    fun getByUserId(userId: String): Set<VehicleEntity>?

}