package dev.alancss.forum.mapper

import dev.alancss.forum.dto.NewTopicRequest
import dev.alancss.forum.dto.TopicResponse
import dev.alancss.forum.model.Course
import dev.alancss.forum.model.Topic
import dev.alancss.forum.model.User
import org.springframework.stereotype.Component

@Component
class TopicMapper {

    fun toResponseDto(topic: Topic) = TopicResponse(
        id = topic.id,
        title = topic.title,
        message = topic.message,
        status = topic.status,
        createdAt = topic.createdAt,
        updatedAt = topic.updatedAt
    )

    fun toTopic(request: NewTopicRequest, course: Course, author: User) = Topic(
        title = request.title,
        message = request.message,
        course = course,
        author = author
    )
}