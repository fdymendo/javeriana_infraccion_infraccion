package com.fdymendo.javeriana.infracciones.service

import org.springframework.data.jpa.repository.JpaRepository


abstract class ACrudServiceTemplate<T : JpaRepository<S, String>, S>(private var repository: T) {

    fun save(item: S) {
        repository.save(item)
    }

    fun deleteById(id: String) {
        repository.deleteById(id)
    }

    fun findAll(): List<S> {
        return repository.findAll()
    }

    fun findById(id: String): S {
        return repository.findById(id).orElseThrow()
    }
}
