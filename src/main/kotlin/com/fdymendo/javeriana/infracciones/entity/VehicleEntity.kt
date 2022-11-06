package com.fdymendo.javeriana.infracciones.entity

import com.fdymendo.javeriana.infracciones.dto.TypePlateDTO
import com.fdymendo.javeriana.infracciones.dto.VehicleDTO
import com.fdymendo.javeriana.infracciones.dto.toEntity
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "vehicle")
data class VehicleEntity(
    @Id
    val id: String,
    val userId: String?,
    val plate: String,
    @ManyToOne
    val typePlate: TypePlateEntity,
    val createDate: Date?,
    val updateDate: Date?
)

fun VehicleEntity.toDTO() = VehicleDTO(
    id = this.id,
    userId = this.userId,
    plate = this.plate,
    typePlate = TypePlateDTO(
        id = this.typePlate.id,
        name = this.typePlate.name
    ),
    createDate = this.createDate,
    updateDate = this.updateDate
)