package com.fdymendo.javeriana.infracciones.handlers

import com.fdymendo.javeriana.infracciones.model.ResponseDefault
import com.fdymendo.javeriana.infracciones.model.ResponseError
import com.fdymendo.javeriana.infracciones.utils.GenericMethods
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import javax.persistence.EntityNotFoundException


@ControllerAdvice
class ApplicationHandler {

    companion object {
        val log: Logger = LoggerFactory.getLogger(ApplicationHandler::class.java)
    }

    @ExceptionHandler(ApplicationException::class)
    fun exceptionFound(ex: ApplicationException, request: WebRequest): ResponseEntity<ResponseError> {
        log.error("Application Exception $ex.message", ex)
        return GenericMethods.genericResponse(ex)
    }

    @ExceptionHandler(EmptyResultDataAccessException::class)
    fun exceptionFound(
        ex: EmptyResultDataAccessException,
        request: WebRequest?
    ): ResponseEntity<ResponseDefault> {
        log.warn(ex.message, ex)
        return GenericMethods.responseBadRequest()
    }

    @ExceptionHandler(Exception::class)
    fun exceptionFound(ex: Exception, request: WebRequest) = when (ex.cause) {
        is EntityNotFoundException -> GenericMethods.responseNotFound()
        else -> {
            log.error("Not Controlled error $ex", ex)
            GenericMethods.responseError500(HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase)
        }
    }
}