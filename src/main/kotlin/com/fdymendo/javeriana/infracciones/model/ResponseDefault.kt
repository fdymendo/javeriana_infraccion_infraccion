package com.fdymendo.javeriana.infracciones.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fdymendo.javeriana.infracciones.dto.InfractionDTO
import com.fdymendo.javeriana.infracciones.dto.VehicleDTO

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ResponseDefault(var infraction: InfractionDTO?, val vehicleDTO: VehicleDTO?)