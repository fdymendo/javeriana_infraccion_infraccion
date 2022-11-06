package com.fdymendo.javeriana.infracciones.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fdymendo.javeriana.infracciones.entity.TypePlateEntity
import com.fdymendo.javeriana.infracciones.entity.VehicleEntity
import com.fdymendo.javeriana.infracciones.utils.GenericMethods
import java.util.*

data class VehicleDTO(
    @JsonProperty("id") var id: String?,
    @JsonProperty("name") var userId: String?,
    @JsonProperty("plate") val plate: String,
    @JsonProperty("typePlate") val typePlate: TypePlateDTO?,
    @JsonProperty("createDate") var createDate: Date?,
    @JsonProperty("updateDate") var updateDate: Date?
)

fun VehicleDTO.toEntity() = VehicleEntity(
    id = this.id ?: UUID.randomUUID().toString(),
    userId = this.userId,
    plate = this.plate,
    typePlate = GenericMethods.typePlate(this.plate),
    createDate = this.createDate ?: Date(),
    updateDate = this.updateDate ?: Date()
)