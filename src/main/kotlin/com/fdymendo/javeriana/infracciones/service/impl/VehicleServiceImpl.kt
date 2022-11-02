package com.fdymendo.javeriana.infracciones.service.impl

import com.fdymendo.javeriana.infracciones.dto.InfractionDTO
import com.fdymendo.javeriana.infracciones.dto.VehicleDTO
import com.fdymendo.javeriana.infracciones.dto.toEntity
import com.fdymendo.javeriana.infracciones.entity.InfractionEntity
import com.fdymendo.javeriana.infracciones.entity.VehicleEntity
import com.fdymendo.javeriana.infracciones.entity.toDTO
import com.fdymendo.javeriana.infracciones.model.ResponseDefault
import com.fdymendo.javeriana.infracciones.repository.InfractionRepository
import com.fdymendo.javeriana.infracciones.repository.VehicleRepository
import com.fdymendo.javeriana.infracciones.service.ACrudServiceTemplate
import com.fdymendo.javeriana.infracciones.service.IInfractionService
import com.fdymendo.javeriana.infracciones.service.IVehicleService
import com.fdymendo.javeriana.infracciones.utils.GenericMethods
import org.springframework.http.ResponseEntity

class VehicleServiceImpl(private val vehicleRepository: VehicleRepository) :
    ACrudServiceTemplate<VehicleRepository, VehicleEntity>(vehicleRepository),
    IVehicleService {

    override fun saveItem(item: VehicleDTO): ResponseEntity<ResponseDefault> {
        item.id = null
        item.createDate = null
        item.updateDate = null
        val itemToSave = item.toEntity()
        vehicleRepository.save(itemToSave)
        return GenericMethods.responseOk(ResponseDefault(null, itemToSave.toDTO()))
    }

    override fun updateItem(item: VehicleDTO, id: String): ResponseEntity<ResponseDefault> {
        TODO("Not yet implemented")
    }

    override fun deleteItem(id: String): ResponseEntity<ResponseDefault> {
        TODO("Not yet implemented")
    }

    override fun getItem(id: String): ResponseEntity<ResponseDefault> {
        TODO("Not yet implemented")
    }

    override fun allItems(): ResponseEntity<ResponseDefault> {
        TODO("Not yet implemented")
    }


}