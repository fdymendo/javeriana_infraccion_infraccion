package com.fdymendo.javeriana.infracciones.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
@RequestMapping("/infraccion")
class OKController {
    @GetMapping
    fun saveInfraction() =  ResponseEntity.ok().body("servicio infraccion")

}