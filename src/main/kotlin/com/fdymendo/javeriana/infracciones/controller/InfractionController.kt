package com.fdymendo.javeriana.infracciones.controller

import com.fdymendo.javeriana.infracciones.dto.InfractionDTO
import com.fdymendo.javeriana.infracciones.dto.UserDTO
import com.fdymendo.javeriana.infracciones.service.IInfractionService
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping("/infraccion/v1")
class InfractionController(val iInfractionService: IInfractionService) {

    @GetMapping("/{id}")
    fun getInfraction(@PathVariable("id") id: String) = iInfractionService.getItem(id)
    @GetMapping("/user")
    fun getInfractionByUser(@RequestParam cc: String, @RequestParam td: String) = iInfractionService.infracctionByUser(cc, td)

    @PostMapping("/placa")
    fun saveInfractionPlate(@RequestBody infraction: InfractionDTO) = iInfractionService.saveItemPlate(infraction)

}