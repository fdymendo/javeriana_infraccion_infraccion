package com.fdymendo.javeriana.infracciones.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fdymendo.javeriana.infracciones.entity.VehicleEntity
import com.fdymendo.javeriana.infracciones.entity.InfractionEntity
import com.fdymendo.javeriana.infracciones.entity.ParkingLotEntity
import com.fdymendo.javeriana.infracciones.entity.TypeInfractionEntity
import java.util.*

data class InfractionDTO(
    @JsonProperty("id") val id: String?,
    @JsonProperty("vehicle") val vehicle: VehicleDTO,
    @JsonProperty("parkingLot") val parkingLot: ParkingLotDTO,
    @JsonProperty("typeInfraction") val typeInfraction: TypeInfractionDTO,
    @JsonProperty("user") var user: UserDTO?,
    @JsonProperty("expirationDate") val expirationDate: Date?,
    @JsonProperty("createDate") val createDate: Date?,
    @JsonProperty("updateDate") val updateDate: Date?,
)

fun InfractionDTO.toEntity() = InfractionEntity(
    id = this.id ?: UUID.randomUUID().toString(),
    vehicle = VehicleEntity(
        id = this.vehicle.id ?: UUID.randomUUID().toString(),
        userId = this.vehicle.userId ?: "",
        plate = this.vehicle.plate,
        createDate = this.vehicle.createDate,
        updateDate = this.vehicle.updateDate
    ),
    parkingLot = ParkingLotEntity(
        id = this.parkingLot.id,
        name = this.parkingLot.name,
        address = this.parkingLot.address,
    ),
    typeInfraction = TypeInfractionEntity(
        id = this.typeInfraction.id,
        code = this.typeInfraction.code,
        detail = this.typeInfraction.detail,
        smdlv = this.typeInfraction.smdlv,
        value = this.typeInfraction.value,
        immobilization = this.typeInfraction.immobilization
    ),
    expirationDate = this.expirationDate ?: Date(),
    createDate = this.createDate ?: Date(),
    updateDate = this.updateDate ?: Date()
)