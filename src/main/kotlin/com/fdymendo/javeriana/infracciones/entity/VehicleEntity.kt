package com.fdymendo.javeriana.infracciones.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "vehicle")
data class VehicleEntity(
    @Id
    val id: String,
    val userId: String?,
    val plate: String?,
    val createDate: Date?,
    val updateDate: Date?
)