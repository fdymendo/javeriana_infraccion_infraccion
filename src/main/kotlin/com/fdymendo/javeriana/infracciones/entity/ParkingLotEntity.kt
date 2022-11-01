package com.fdymendo.javeriana.infracciones.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "parking_lot")
data class ParkingLotEntity(
    @Id
    val id: String,
    val name: String?,
    val address: String?
)