package com.fdymendo.javeriana.infracciones.service.impl

import com.fdymendo.javeriana.infracciones.dto.InfractionDTO
import com.fdymendo.javeriana.infracciones.dto.toEntity
import com.fdymendo.javeriana.infracciones.entity.InfractionEntity
import com.fdymendo.javeriana.infracciones.entity.toDTO
import com.fdymendo.javeriana.infracciones.model.ResponseDefault
import com.fdymendo.javeriana.infracciones.repository.InfractionRepository
import com.fdymendo.javeriana.infracciones.repository.VehicleRepository
import com.fdymendo.javeriana.infracciones.service.ACrudServiceTemplate
import com.fdymendo.javeriana.infracciones.service.IInfractionService
import com.fdymendo.javeriana.infracciones.utils.GenericMethods
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Service
class InfractionServiceImpl(
    private val infractionRepository: InfractionRepository,
    private val vehicleRepository: VehicleRepository
) :
    ACrudServiceTemplate<InfractionRepository, InfractionEntity>(infractionRepository), IInfractionService {

    override fun saveItem(item: InfractionDTO): ResponseEntity<ResponseDefault> {

        var itemToSave = item.toEntity()
        itemToSave.id = UUID.randomUUID().toString();
        itemToSave.expirationDate = generateExpiration()
        itemToSave.createDate = Date()
        itemToSave.updateDate = Date()

        this.infractionRepository.save(itemToSave)
        return GenericMethods.responseOk(ResponseDefault(itemToSave.toDTO()))
    }

    override fun saveItemPlate(item: InfractionDTO): ResponseEntity<ResponseDefault> {
        item.vehicle.plate?.let {
            val vehicle = vehicleRepository.getByPlate(it)
            val itemToSave = InfractionEntity(
                id = UUID.randomUUID().toString(),
                vehicle = vehicle,
                expirationDate = generateExpiration(),
                createDate = Date(),
                updateDate = Date()
            )
            this.infractionRepository.save(itemToSave)
            return GenericMethods.responseOk(ResponseDefault(itemToSave.toDTO()))
        }
        return GenericMethods.responseNotFound()

    }

    override fun getItem(id: String): ResponseEntity<ResponseDefault> {
        this.infractionRepository.getReferenceById(id).toDTO().let {

            return GenericMethods.responseOk(ResponseDefault(it))
        }
    }

    override fun updateItem(item: InfractionDTO, id: String): ResponseEntity<ResponseDefault> =
        TODO("Not yet implemented")

    override fun deleteItem(id: String): ResponseEntity<ResponseDefault> = TODO("Not yet implemented")


    override val allItems: ResponseEntity<Any>
        get() = TODO("Not yet implemented")

    private fun generateExpiration() = Date.from(LocalDateTime.now().plusDays(1L).toInstant(ZoneOffset.UTC))
}
