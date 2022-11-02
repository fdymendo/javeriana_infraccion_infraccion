package com.fdymendo.javeriana.infracciones.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fdymendo.javeriana.infracciones.model.user.TypeDocument
import com.fdymendo.javeriana.infracciones.model.user.UserResponse
import com.fdymendo.javeriana.infracciones.model.user.getTypeDocument
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserDTO(
    @JsonProperty("id") var id: String?,
    @JsonProperty("name") val name: String,
    @JsonProperty("surname") val surname: String,
    @JsonProperty("email") val email: String,
    @JsonProperty("typeDocument") val typeDocument: String,
    @JsonProperty("document") val document: String,
)

fun UserDTO.toUserRequest(): UserResponse = UserResponse(
    id = null,
    name = this.name,
    surname = this.surname,
    email = this.email,
    typeDocument = TypeDocument(
        id = getTypeDocument(this.typeDocument),
        name = null,
        abbreviation = this.typeDocument
    ),
    document = this.document,
    password = UUID.randomUUID().toString(),
    active = null,
    createDate = null,
    updateDate = null
)