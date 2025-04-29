package dev.alancss.forum.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime
import java.time.OffsetDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorResponse(
    val timestamp: OffsetDateTime = OffsetDateTime.now(),
    val status: Int,
    val error: String,
    val message: String? = null,
    val path: String,
    val errors: List<FieldError>? = null
)