package com.fdymendo.javeriana.infracciones.handlers

import org.springframework.http.HttpStatus

class ApplicationException(e: Exception?, message: String, httpStatus: HttpStatus) :

    Exception(message, e) {

    private val httpStatus: HttpStatus

    init {
        this.httpStatus = httpStatus
    }

    fun getHttpStatus() = httpStatus
    companion object {
        private const val serialVersionUID = 1L
    }

}