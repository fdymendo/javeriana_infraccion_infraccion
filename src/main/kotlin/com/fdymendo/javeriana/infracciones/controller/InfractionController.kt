package com.fdymendo.javeriana.infracciones.controller

import com.fdymendo.javeriana.infracciones.dto.InfractionDTO
import com.fdymendo.javeriana.infracciones.service.IInfractionService
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping("/infraccion/v1")
class InfractionController(val iUserService: IInfractionService) {

    @GetMapping("/{id}")
    fun getInfraction(@PathVariable("id") id: String) = iUserService.getItem(id)

    @PostMapping
    fun saveInfraction(@RequestBody infraction: InfractionDTO) = iUserService.saveItem(infraction)

    @PostMapping("/placa")
    fun saveInfractionPlate(@RequestBody infraction: InfractionDTO) = iUserService.saveItemPlate(infraction)

}