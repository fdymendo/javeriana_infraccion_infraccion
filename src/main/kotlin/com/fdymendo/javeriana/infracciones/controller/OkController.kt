package com.fdymendo.javeriana.infracciones.controller

import com.fdymendo.javeriana.infracciones.dto.InfractionDTO
import com.fdymendo.javeriana.infracciones.service.IInfractionService
import com.fdymendo.javeriana.infracciones.utils.GenericMethods
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/")
class OKController {

    @GetMapping
    fun saveInfraction() = ResponseEntity.ok()

}