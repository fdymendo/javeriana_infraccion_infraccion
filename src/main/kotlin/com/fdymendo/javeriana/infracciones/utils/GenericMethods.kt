package com.fdymendo.javeriana.infracciones.utils

import com.fdymendo.javeriana.infracciones.entity.TypePlateEntity
import com.fdymendo.javeriana.infracciones.handlers.ApplicationException
import com.fdymendo.javeriana.infracciones.model.ResponseDefault
import com.fdymendo.javeriana.infracciones.model.ResponseError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity


class GenericMethods {
    companion object {
        fun responseOk(): ResponseEntity<String> {
            return ResponseEntity.ok().body(HttpStatus.OK.reasonPhrase)
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

        fun typePlate(plate: String): TypePlateEntity {
            return when (plate.substring(0,1)) {
                "B" -> TypePlateEntity("2", "Comercial")
                "D" -> TypePlateEntity("3", "Diplomatico")
                "R" -> TypePlateEntity("4", "Remolque")
                "M" -> TypePlateEntity("5", "Misión especial")
                "A" -> TypePlateEntity("6", "Auto antiguo")
                else -> TypePlateEntity("1", "Particular")
            }
        }
    }
}