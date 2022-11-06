package com.fdymendo.javeriana.infracciones.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


class TypePlateDTO(
    @JsonProperty("id")  val id: String,
    @JsonProperty("name")  val name: String?
)