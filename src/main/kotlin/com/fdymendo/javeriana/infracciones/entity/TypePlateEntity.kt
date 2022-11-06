package com.fdymendo.javeriana.infracciones.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "type_plate")
class TypePlateEntity(
    @Id
    val id: String,
    val name: String?
)