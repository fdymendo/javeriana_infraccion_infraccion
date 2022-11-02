package com.fdymendo.javeriana.infracciones.service.impl

import com.fdymendo.javeriana.infracciones.dto.*
import com.fdymendo.javeriana.infracciones.entity.*
import com.fdymendo.javeriana.infracciones.handlers.ApplicationException
import com.fdymendo.javeriana.infracciones.model.ResponseDefault
import com.fdymendo.javeriana.infracciones.model.user.ResponseUser
import com.fdymendo.javeriana.infracciones.model.user.UserResponse
import com.fdymendo.javeriana.infracciones.model.user.toDTO
import com.fdymendo.javeriana.infracciones.repository.InfractionRepository
import com.fdymendo.javeriana.infracciones.repository.VehicleRepository
import com.fdymendo.javeriana.infracciones.service.ACrudServiceTemplate
import com.fdymendo.javeriana.infracciones.service.IInfractionService
import com.fdymendo.javeriana.infracciones.utils.GenericMethods
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*


@Service
class InfractionServiceImpl(
    private val infractionRepository: InfractionRepository,
    private val vehicleRepository: VehicleRepository,
    @Value("\${api.internal.services.users.ip}")
    private val ip: String,
    @Value("\${api.internal.services.users.path.create}")
    private val pathGet: String,
    @Value("\${api.internal.services.users.path.getUser}")
    private val pathCreate: String

) :
    ACrudServiceTemplate<InfractionRepository, InfractionEntity>(infractionRepository), IInfractionService {

    override fun saveItem(item: InfractionDTO): ResponseEntity<ResponseDefault> {

        var itemToSave = item.toEntity()
        itemToSave.id = UUID.randomUUID().toString();
        itemToSave.expirationDate = generateExpiration()
        itemToSave.createDate = Date()
        itemToSave.updateDate = Date()

        this.infractionRepository.save(itemToSave)
        return GenericMethods.responseOk(ResponseDefault(itemToSave.toDTO(), null))
    }

    override fun saveItemPlate(item: InfractionDTO): ResponseEntity<ResponseDefault> {
        val vehicle = validateVehicle(item.vehicle, item.user!!)
        //buscar parkinglot
        val itemToSave = InfractionEntity(
            id = UUID.randomUUID().toString(),
            vehicle = vehicle,
            parkingLot = ParkingLotEntity(
                id = item.parkingLot.id,
                name = item.parkingLot.name,
                address = item.parkingLot.address,
            ),
            typeInfraction = TypeInfractionEntity(
                id = item.typeInfraction.id,
                code = item.typeInfraction.code,
                detail = item.typeInfraction.detail,
                smdlv = item.typeInfraction.smdlv,
                value = item.typeInfraction.value,
                immobilization = item.typeInfraction.immobilization
            ),
            expirationDate = generateExpiration(),
            createDate = Date(),
            updateDate = Date()
        )
        this.infractionRepository.save(itemToSave)
        return GenericMethods.responseOk(ResponseDefault(itemToSave.toDTO(), null))
    }

    override fun getItem(id: String): ResponseEntity<ResponseDefault> {

        this.infractionRepository.getReferenceById(id).toDTO().let {
            it.user = it.vehicle.userId?.let { it1 -> getPersonById(it1).toDTO() }
            return GenericMethods.responseOk(ResponseDefault(it, null))
        }

    }

    override fun allItems(): ResponseEntity<ResponseDefault> {
        TODO("Not yet implemented")
    }

    override fun updateItem(item: InfractionDTO, id: String): ResponseEntity<ResponseDefault> =
        TODO("Not yet implemented")

    override fun deleteItem(id: String): ResponseEntity<ResponseDefault> = TODO("Not yet implemented")

    private fun generateExpiration() = Date.from(LocalDateTime.now().plusDays(1L).toInstant(ZoneOffset.UTC))

    private fun validateVehicle(vehicleDTO: VehicleDTO, userDTO: UserDTO): VehicleEntity {
        val person = getPerson(userDTO)
        return getVehicle(vehicleDTO, person.toDTO())

    }

    private fun getPerson(userDTO: UserDTO): UserResponse {
        val urlGet = "${ip}${pathGet}?cc=${userDTO.document}&td=${userDTO.typeDocument}"
        val urlPost = "${ip}${pathCreate}?cc=${userDTO.document}&td=${userDTO.typeDocument}"

        return try {
            val response = RestTemplate().exchange(urlGet, HttpMethod.GET, null, ResponseUser::class.java)
            response.body?.user!!
        } catch (e: Exception) {
            val request = HttpEntity(userDTO.toUserRequest())
            val response = RestTemplate().exchange(urlPost, HttpMethod.POST, request, ResponseUser::class.java)
            response.body?.user!!
        }
    }

    private fun getVehicle(vehicleDTO: VehicleDTO, userDTO: UserDTO): VehicleEntity {
        val vehicleEntity = vehicleRepository.getByPlate(vehicleDTO.plate)
        vehicleEntity?.let {
            return it
        }
        vehicleDTO.userId = userDTO.id
        return vehicleRepository.save(vehicleDTO.toEntity())
    }

    private fun getPersonById(id: String): UserResponse {

        val urlGetId = "${ip}${pathGet}/${id}"

        return try {
            val response = RestTemplate().exchange(urlGetId, HttpMethod.GET, null, ResponseUser::class.java)
            response.body?.user!!
        } catch (e: Exception) {
            throw ApplicationException(null, HttpStatus.NOT_FOUND.reasonPhrase, HttpStatus.SERVICE_UNAVAILABLE)
        }
    }
}
