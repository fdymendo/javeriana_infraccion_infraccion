package com.fdymendo.javeriana.infracciones.model.user

import com.fasterxml.jackson.annotation.JsonProperty

data class TypeDocument(
    @JsonProperty("id") val id: String,
    @JsonProperty("name") val name: String?,
    @JsonProperty("abbreviation") val abbreviation: String
)