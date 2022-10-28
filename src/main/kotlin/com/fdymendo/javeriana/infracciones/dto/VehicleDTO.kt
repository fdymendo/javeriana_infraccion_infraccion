package com.fdymendo.javeriana.infracciones.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class VehicleDTO(
    @JsonProperty("id") val id: String?,
    @JsonProperty("name") val userId: String?,
    @JsonProperty("plate") val plate: String?,
    @JsonProperty("createDate") val createDate: Date?,
    @JsonProperty("updateDate") val updateDate: Date?
)
