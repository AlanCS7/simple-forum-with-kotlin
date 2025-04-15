package dev.alancss.forum.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@JsonIgnoreProperties(ignoreUnknown = true)
data class NewTopicDto(
    @field:NotBlank(message = "Title is required")
    val title: String,
    @field:NotBlank(message = "Message is required")
    val message: String,
    @field:NotNull(message = "Course is required")
    val courseId: Long,
    @field:NotNull(message = "Author is required")
    val authorId: Long
)
