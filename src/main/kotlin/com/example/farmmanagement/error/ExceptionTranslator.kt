package com.example.farmmanagement.error

import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ExceptionTranslator: ResponseEntityExceptionHandler() {

    val log = LoggerFactory.getLogger(ExceptionTranslator::class.java.name)


    /**
     * Handles java.util.NoSuchElementException. Thrown when entity lookup fails in service layer.
     */
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElement(
        ex: NoSuchElementException?
    ): Any {
        return ResponseEntity(mapOf("errors" to "Resource not found."), HttpStatus.NOT_FOUND)
    }


    /**
     * Handles HttpMessageNotReadableException. Happens when request JSON is malformed.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val servletWebRequest = request as ServletWebRequest
        log.info("{} to {}", servletWebRequest.httpMethod, servletWebRequest.request.servletPath)
        return ResponseEntity(mapOf("errors" to "Malformed JSON request", "message" to ex.localizedMessage), HttpStatus.BAD_REQUEST)
    }

    /**
     * Handles java.util.NoSuchElementException. Thrown when entity lookup fails in service layer.
     */
    @ExceptionHandler(InvalidRequestException::class)
    fun handleInvalidRequestException(
        ex: InvalidRequestException?
    ): Any {
        return ResponseEntity(mapOf("errors" to ex?.message), HttpStatus.BAD_REQUEST)
    }


}