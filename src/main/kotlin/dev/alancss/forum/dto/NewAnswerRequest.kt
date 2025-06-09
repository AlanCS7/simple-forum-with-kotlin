package dev.alancss.forum.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class NewAnswerRequest(
    @field:NotBlank(message = "Message is required")
    val message: String,
    @field:NotNull(message = "Author is required")
    val topicId: Long,
    val solution: Boolean? = false
)
