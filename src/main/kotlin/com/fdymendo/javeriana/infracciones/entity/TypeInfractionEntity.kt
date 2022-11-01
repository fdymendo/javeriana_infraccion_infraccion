package com.fdymendo.javeriana.infracciones.entity

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "type_infraction")
data class TypeInfractionEntity(
    @Id
    val id: String,
    val code: String?,
    val detail: String?,
    val smdlv: Int?,
    @Column(columnDefinition = "DECIMAL")
    val value: Double?,
    @Column(columnDefinition = "TINYINT")
    val immobilization: Boolean?
)