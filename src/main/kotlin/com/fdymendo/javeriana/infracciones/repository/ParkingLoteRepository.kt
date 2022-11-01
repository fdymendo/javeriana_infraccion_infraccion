package com.fdymendo.javeriana.infracciones.repository

import com.fdymendo.javeriana.infracciones.entity.ParkingLotEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ParkingLoteRepository : JpaRepository<ParkingLotEntity, String> {

}