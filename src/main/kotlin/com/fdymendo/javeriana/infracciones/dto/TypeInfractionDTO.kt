package com.fdymendo.javeriana.infracciones.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

data class TypeInfractionDTO(
    @JsonProperty("id") val id: String,
    @JsonProperty("code") val code: String?,
    @JsonProperty("detail") val detail: String?,
    @JsonProperty("smdlv") val smdlv: Int?,
    @JsonProperty("value") val value: Double?,
    @JsonProperty("immobilization") val immobilization: Boolean?
)