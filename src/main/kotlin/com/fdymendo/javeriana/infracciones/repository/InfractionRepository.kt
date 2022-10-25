package com.fdymendo.javeriana.infracciones.repository

import com.fdymendo.javeriana.infracciones.entity.InfractionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface InfractionRepository: JpaRepository<InfractionEntity, String>{

}