package dev.alancss.forum.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.validation.constraints.NotBlank

@JsonIgnoreProperties(ignoreUnknown = true)
data class UpdateTopicRequest(
    @field:NotBlank(message = "Title is required")
    val title: String,
    @field:NotBlank(message = "Message is required")
    val message: String
)
