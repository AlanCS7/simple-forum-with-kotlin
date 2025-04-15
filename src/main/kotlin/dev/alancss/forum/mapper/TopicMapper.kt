package dev.alancss.forum.mapper

import dev.alancss.forum.dto.NewTopicDto
import dev.alancss.forum.dto.TopicResponseDto
import dev.alancss.forum.model.Course
import dev.alancss.forum.model.Topic
import dev.alancss.forum.model.User
import org.springframework.stereotype.Component

@Component
class TopicMapper {

    fun toResponseDto(topic: Topic) = TopicResponseDto(
        id = topic.id,
        title = topic.title,
        message = topic.message,
        status = topic.status,
        createdAt = topic.createdAt
    )

    fun toTopic(dto: NewTopicDto, course: Course, author: User) = Topic(
        title = dto.title,
        message = dto.message,
        course = course,
        author = author
    )
}