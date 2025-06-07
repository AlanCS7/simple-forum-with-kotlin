package dev.alancss.forum.mapper

import dev.alancss.forum.dto.TopicResponse
import dev.alancss.forum.enum.TopicStatus
import java.time.LocalDateTime

object TopicMapperTestFactory {

    fun toResponseDto(
        id: Long? = 1L,
        title: String = "Error in Kotlin code",
        message: String = "Var x is not defined in the current scope",
        status: TopicStatus = TopicStatus.NOT_ANSWERED,
        createdAt: LocalDateTime? = LocalDateTime.now(),
        updatedAt: LocalDateTime? = null
    ): TopicResponse {
        return TopicResponse(
            id = id,
            title = title,
            message = message,
            status = status,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}