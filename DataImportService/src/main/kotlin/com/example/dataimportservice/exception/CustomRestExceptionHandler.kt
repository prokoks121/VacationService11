package com.example.dataimportservice.exception

import com.example.dataimportservice.model.ErrorResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.function.Consumer

@ControllerAdvice
class CustomRestExceptionHandler: ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errors: MutableList<String> = ArrayList()
        for (error in ex.bindingResult.fieldErrors) {
            errors.add(error.field + ": " + error.defaultMessage)
        }
        for (error in ex.bindingResult.globalErrors) {
            errors.add(error.objectName + ": " + error.defaultMessage)
        }
        val apiError = ErrorResponse(HttpStatus.BAD_REQUEST, ex.localizedMessage, errors)
        return handleExceptionInternal(
            ex, apiError, headers, apiError.status, request
        )
    }

    override fun handleMissingServletRequestParameter(
        ex: MissingServletRequestParameterException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val error = ex.parameterName + " parameter is missing"
        val apiError = ErrorResponse(HttpStatus.BAD_REQUEST, ex.localizedMessage, arrayListOf(error))
        return ResponseEntity<Any>(
            apiError, HttpHeaders(), apiError.status
        )
    }

    override fun handleHttpMediaTypeNotSupported(
        ex: HttpMediaTypeNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val builder = StringBuilder()
        builder.append(ex.contentType)
        builder.append(" media type is not supported. Supported media types are ")
        ex.supportedMediaTypes.forEach(Consumer { t: MediaType ->
            builder.append(
                "$t, "
            )
        })
        val apiError = ErrorResponse(
            HttpStatus.UNSUPPORTED_MEDIA_TYPE,
            ex.localizedMessage, arrayListOf(builder.substring(0, builder.length - 2))
        )
        return ResponseEntity<Any>(
            apiError, HttpHeaders(), apiError.status
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleAll(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val apiError = ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR, ex.localizedMessage, arrayListOf("error occurred")
        )
        return ResponseEntity<Any>(
            apiError, HttpHeaders(), apiError.status
        )
    }
}