package com.fdymendo.javeriana.infracciones.service.impl

import com.fasterxml.jackson.annotation.JsonProperty
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
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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
import java.util.stream.Collectors


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
    companion object {
        val logger: Logger = LoggerFactory.getLogger(InfractionServiceImpl::class.java)
    }

    override fun saveItem(item: InfractionDTO): ResponseEntity<ResponseDefault> {
        logger.info("saveItem $item")

        var itemToSave = item.toEntity()
        itemToSave.id = UUID.randomUUID().toString();
        itemToSave.expirationDate = generateExpiration()
        itemToSave.createDate = Date()
        itemToSave.updateDate = Date()

        this.infractionRepository.save(itemToSave)
        return GenericMethods.responseOk(ResponseDefault(mutableListOf(itemToSave.toDTO()), null))
    }

    override fun saveItemPlate(item: InfractionDTO): ResponseEntity<ResponseDefault> {
        logger.info("saveItemPlate $item")
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
        return GenericMethods.responseOk(ResponseDefault(mutableListOf(itemToSave.toDTO()), null))
    }

    override fun getItem(id: String): ResponseEntity<ResponseDefault> {
        logger.info("getItem $id")

        this.infractionRepository.getReferenceById(id).toDTO().let {
            it.user = it.vehicle.userId?.let { it1 -> getPersonById(it1).toDTO() }
            return GenericMethods.responseOk(ResponseDefault(mutableListOf(it), null))
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
        logger.info("validateVehicle $vehicleDTO, $userDTO")
        val person = getPerson(userDTO)
        return getVehicle(vehicleDTO, person.toDTO())

    }

    private fun getPerson(userDTO: UserDTO): UserResponse {
        logger.info("getPerson $userDTO")

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
        logger.info("getVehicle $vehicleDTO, $userDTO")
        val vehicleEntity = vehicleRepository.getByPlate(vehicleDTO.plate)
        vehicleEntity?.let {
            return it
        }
        vehicleDTO.userId = userDTO.id
        return vehicleRepository.save(vehicleDTO.toEntity())
    }

    private fun getPersonById(id: String): UserResponse {
        logger.info("getPersonById $id")

        val urlGetId = "${ip}${pathGet}/${id}"

        return try {
            val response = RestTemplate().exchange(urlGetId, HttpMethod.GET, null, ResponseUser::class.java)
            response.body?.user!!
        } catch (e: Exception) {
            throw ApplicationException(null, HttpStatus.NOT_FOUND.reasonPhrase, HttpStatus.SERVICE_UNAVAILABLE)
        }
    }

    override fun infracctionByUser(cc: String, td: String): ResponseEntity<ResponseDefault> {

        logger.info("Search infracction cc: $cc and td: $td")
        println("Search infracction cc: $cc and td: $td")
        val person = getPerson(cc = cc, td = td)
        val infractions = mutableListOf<InfractionDTO>()

        person.id?.let { personId ->
            println("Search infracction getByUserId $personId")
            val vehicles = this.vehicleRepository.getByUserId(personId)
            vehicles?.forEach { vehicle ->
                this.infractionRepository.getByVehicle(vehicle).let { infractionsEntity ->
                    infractions.addAll(infractionsEntity.stream().map { entity -> entity.toDTO() }
                        .collect(Collectors.toList()))
                }
            }
        }
        return GenericMethods.responseOk(ResponseDefault(infractions, null))
    }

    private fun getPerson(cc: String, td: String) = getPerson(
        UserDTO(
            id = null,
            name = "",
            surname = "",
            email = "",
            typeDocument = td,
            document = cc,
        )
    )

}
