package com.fdymendo.javeriana.infracciones.utils

import com.fdymendo.javeriana.infracciones.handlers.ApplicationException
import com.fdymendo.javeriana.infracciones.model.ResponseDefault
import com.fdymendo.javeriana.infracciones.model.ResponseError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity


class GenericMethods {
    companion object {
        fun responseOk(): ResponseEntity<ResponseDefault> {
            return ResponseEntity.ok().body(null)
        }
        fun responseOk(responseDefault: ResponseDefault): ResponseEntity<ResponseDefault> {
            return ResponseEntity.ok(responseDefault)
        }

        fun responseNotFound(): ResponseEntity<ResponseDefault> {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }

        fun responseError500(message: String): ResponseEntity<ResponseError> {
            return ResponseEntity.internalServerError().body(ResponseError(message))
        }

        fun genericResponse(
            applicationException: ApplicationException
        ): ResponseEntity<ResponseError> {
            return ResponseEntity.status(applicationException.getHttpStatus())
                .body<ResponseError>(
                    ResponseError(
                        applicationException.message ?: HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase
                    )
                )
        }

        fun responseBadRequest(): ResponseEntity<ResponseDefault> {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        }
    }
}