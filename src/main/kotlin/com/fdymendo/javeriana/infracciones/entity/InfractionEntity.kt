package com.fdymendo.javeriana.infracciones.entity

import com.fdymendo.javeriana.infracciones.dto.InfractionDTO
import com.fdymendo.javeriana.infracciones.dto.ParkingLotDTO
import com.fdymendo.javeriana.infracciones.dto.TypeInfractionDTO
import com.fdymendo.javeriana.infracciones.dto.VehicleDTO
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "infraction")
data class InfractionEntity(
    @Id var id: String = UUID.randomUUID().toString(),
    @ManyToOne
    val vehicle: VehicleEntity,
    @ManyToOne
    val parkingLot: ParkingLotEntity,
    @ManyToOne
    val typeInfraction: TypeInfractionEntity,
    var expirationDate: Date,
    var createDate: Date,
    var updateDate: Date,
)

fun InfractionEntity.toDTO() = InfractionDTO(
    id = this.id,
    vehicle = VehicleDTO(
        id = this.vehicle.id,
        userId = this.vehicle.userId ?: "",
        plate = this.vehicle.plate ?: "",
        createDate = this.vehicle.createDate,
        updateDate = this.vehicle.updateDate
    ),
    parkingLot = ParkingLotDTO(
        id = this.parkingLot.id,
        name = this.parkingLot.name,
        address = this.parkingLot.address,
    ),
    typeInfraction = TypeInfractionDTO(
        id = this.typeInfraction.id,
        code = this.typeInfraction.code,
        detail = this.typeInfraction.detail,
        smdlv = this.typeInfraction.smdlv,
        value = this.typeInfraction.value,
        immobilization = this.typeInfraction.immobilization
    ),
    expirationDate = this.expirationDate,
    createDate = this.createDate,
    updateDate = this.updateDate
)