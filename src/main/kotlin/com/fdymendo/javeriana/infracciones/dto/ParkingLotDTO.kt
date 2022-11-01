package com.fdymendo.javeriana.infracciones.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ParkingLotDTO(
    @JsonProperty("id") val id: String,
    @JsonProperty("name") val name: String?,
    @JsonProperty("address") val address: String?
)