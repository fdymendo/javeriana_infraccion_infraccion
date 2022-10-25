package com.fdymendo.javeriana.infracciones.entity

import com.fdymendo.javeriana.infracciones.dto.VehicleDTO
import com.fdymendo.javeriana.infracciones.dto.InfractionDTO
import com.fdymendo.javeriana.infracciones.dto.toEntity
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "infraction")
data class InfractionEntity(
    @Id var id: String = UUID.randomUUID().toString(),
    @ManyToOne
    val vehicle: VehicleEntity,
    var expirationDate: Date,
    var createDate: Date,
    var updateDate: Date,
)

fun InfractionEntity.toDTO() = InfractionDTO(
    id = this.id,
    vehicle = VehicleEntity(
        id = this.vehicle.id,
        userId = this.vehicle.userId ?: "",
        plate = this.vehicle.plate ?: "",
        createDate = this.vehicle.createDate,
        updateDate = this.vehicle.updateDate
    ),
    expirationDate = this.expirationDate,
    createDate = this.createDate,
    updateDate = this.updateDate
)