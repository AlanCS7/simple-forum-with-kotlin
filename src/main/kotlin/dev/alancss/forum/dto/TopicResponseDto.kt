package dev.alancss.forum.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import dev.alancss.forum.enum.TopicStatus
import java.time.LocalDateTime

@JsonInclude(Include.NON_NULL)
data class TopicResponseDto(
    val id: Long?,
    val title: String,
    val message: String,
    val status: TopicStatus,
    val createdAt: LocalDateTime
)
