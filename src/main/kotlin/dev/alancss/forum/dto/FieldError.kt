package dev.alancss.forum.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class FieldError(
    val field: String,
    val message: String
)
