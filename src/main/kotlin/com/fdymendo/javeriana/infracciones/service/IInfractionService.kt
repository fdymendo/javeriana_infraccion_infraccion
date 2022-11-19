package com.fdymendo.javeriana.infracciones.service

import com.fdymendo.javeriana.infracciones.dto.InfractionDTO
import com.fdymendo.javeriana.infracciones.model.ResponseDefault
import org.springframework.http.ResponseEntity

interface IInfractionService : ICrudTemplate<InfractionDTO> {
    fun saveItemPlate(item: InfractionDTO): ResponseEntity<ResponseDefault>
    fun infracctionByUser(cc: String, td: String): ResponseEntity<ResponseDefault>

}