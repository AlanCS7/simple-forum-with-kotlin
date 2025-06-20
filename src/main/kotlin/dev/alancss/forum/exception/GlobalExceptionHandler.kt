package dev.alancss.forum.exception

import dev.alancss.forum.dto.ErrorResponse
import dev.alancss.forum.dto.FieldError
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(BusinessException::class)
    @ResponseStatus(BAD_REQUEST)
    fun handleBusinessException(
        exception: BusinessException,
        httpServletRequest: HttpServletRequest
    ): ErrorResponse =
        ErrorResponse(
            status = BAD_REQUEST.value(),
            error = "Business Exception",
            message = exception.message,
            path = httpServletRequest.requestURI
        )


    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(
        exception: ResourceNotFoundException,
        httpServletRequest: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val status = if (httpServletRequest.method == "POST") BAD_REQUEST else NOT_FOUND

        val errorResponse = ErrorResponse(
            status = status.value(),
            error = status.name,
            message = exception.message,
            path = httpServletRequest.requestURI
        )

        return ResponseEntity.status(status).body(errorResponse)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(BAD_REQUEST)
    fun handleMethodArgumentNotValidException(
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
    fun handleHttpMessageNotReadableException(
        exception: HttpMessageNotReadableException,
        httpServletRequest: HttpServletRequest
    ): ErrorResponse =
        ErrorResponse(
            status = BAD_REQUEST.value(),
            error = BAD_REQUEST.name,
            message = "Invalid request format. Please check your request body and try again.",
            path = httpServletRequest.requestURI
        )

    @ExceptionHandler(BadCredentialsException::class)
    @ResponseStatus(UNAUTHORIZED)
    fun handleBadCredentialsException(
        exception: BadCredentialsException,
        httpServletRequest: HttpServletRequest
    ): ErrorResponse =
        ErrorResponse(
            status = UNAUTHORIZED.value(),
            error = UNAUTHORIZED.name,
            message = "Invalid username or password",
            path = httpServletRequest.requestURI
        )

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(exception: Exception, httpServletRequest: HttpServletRequest): ErrorResponse {
        logger.error("Unexpected error", exception)

        val message = "An unexpected error occurred: ${exception.localizedMessage}"
        return ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = HttpStatus.INTERNAL_SERVER_ERROR.name,
            message = message,
            path = httpServletRequest.requestURI
        )
    }

}