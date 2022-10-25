package com.fdymendo.javeriana.infracciones.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fdymendo.javeriana.infracciones.entity.VehicleEntity
import com.fdymendo.javeriana.infracciones.entity.InfractionEntity
import java.util.*

data class InfractionDTO(
    @JsonProperty("id") val id: String?,
    @JsonProperty("vehicle") val vehicle: VehicleEntity,
    @JsonProperty("expirationDate") val expirationDate: Date?,
    @JsonProperty("createDate") val createDate: Date?,
    @JsonProperty("updateDate") val updateDate: Date?,
)

fun InfractionDTO.toEntity() = InfractionEntity(
    id = this.id ?: UUID.randomUUID().toString(),
    vehicle = VehicleEntity(
        id = this.vehicle.id,
        userId = this.vehicle.userId ?: "",
        plate = this.vehicle.plate ?: "",
        createDate = this.vehicle.createDate,
        updateDate = this.vehicle.updateDate
    ),
    expirationDate = this.expirationDate ?: Date(),
    createDate = this.createDate ?: Date(),
    updateDate = this.updateDate ?: Date()
)