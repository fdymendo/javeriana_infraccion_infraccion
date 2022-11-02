package com.fdymendo.javeriana.infracciones.model.user

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fdymendo.javeriana.infracciones.dto.UserDTO
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserResponse(
    @JsonProperty("id") var id: String?,
    @JsonProperty("name") val name: String,
    @JsonProperty("surname") val surname: String,
    @JsonProperty("email") val email: String,
    @JsonProperty("typeDocument") val typeDocument: TypeDocument,
    @JsonProperty("document") val document: String,
    @JsonProperty("password") var password: String?,
    @JsonProperty("active") var active: Boolean?,
    @JsonProperty("createDate") var createDate: Date?,
    @JsonProperty("updateDate") var updateDate: Date?,
)

fun UserResponse.toDTO() = UserDTO(
    id = this.id,
    name = this.name,
    surname = this.surname,
    email = this.email,
    typeDocument = this.typeDocument.abbreviation,
    document = this.document
)

fun getTypeDocument(value: String) = when (value){
    "CC" -> "1"
    "TI" -> "2"
    "PA" -> "3"
    else -> "1"
}