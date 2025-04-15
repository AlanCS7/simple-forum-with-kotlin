package dev.alancss.forum.exception

import dev.alancss.forum.dto.ErrorResponse
import dev.alancss.forum.dto.FieldError
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun handleException(exception: ResourceNotFoundException, httpServletRequest: HttpServletRequest): ErrorResponse =
        ErrorResponse(
            status = NOT_FOUND.value(),
            error = NOT_FOUND.name,
            message = exception.message,
            path = httpServletRequest.requestURI
        )

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(BAD_REQUEST)
    fun handleException(
        exception: MethodArgumentNotValidException,
        httpServletRequest: HttpServletRequest
    ): ErrorResponse =
        ErrorResponse(
            status = BAD_REQUEST.value(),
            error = BAD_REQUEST.name,
            message = "Validation failed for one or more fields.",
            path = httpServletRequest.requestURI,
            errors = exception.bindingResult.fieldErrors
                .sortedBy { it.field }
                .map {
                    FieldError(
                        field = it.field,
                        message = it.defaultMessage ?: "Invalid value"
                    )
                }
        )

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(BAD_REQUEST)
    fun handleException(
        exception: HttpMessageNotReadableException,
        httpServletRequest: HttpServletRequest
    ): ErrorResponse =
        ErrorResponse(
            status = BAD_REQUEST.value(),
            error = BAD_REQUEST.name,
            message = "Invalid request format. Please check your request body and try again.",
            path = httpServletRequest.requestURI
        )

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(exception: Exception, httpServletRequest: HttpServletRequest): ErrorResponse {
        exception.printStackTrace()
        return ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = HttpStatus.INTERNAL_SERVER_ERROR.name,
            message = exception.message,
            path = httpServletRequest.requestURI
        )
    }

}