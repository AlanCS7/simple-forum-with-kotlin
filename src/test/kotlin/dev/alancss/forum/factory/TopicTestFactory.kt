package dev.alancss.forum.factory

import dev.alancss.forum.dto.NewTopicDto
import dev.alancss.forum.dto.UpdateTopicDto
import dev.alancss.forum.enum.TopicStatus
import dev.alancss.forum.model.Course
import dev.alancss.forum.model.Topic
import dev.alancss.forum.model.User
import java.time.LocalDateTime

object TopicTestFactory {

    fun build(
        id: Long? = 1L,
        title: String = "Error in Kotlin code",
        message: String = "Var x is not defined in the current scope",
        course: Course = CourseTestFactory.build(),
        author: User = UserTestFactory.build(),
        status: TopicStatus = TopicStatus.NOT_ANSWERED,
        createdAt: LocalDateTime? = LocalDateTime.now(),
        updatedAt: LocalDateTime? = null
    ): Topic {
        return Topic(
            id = id,
            title = title,
            message = message,
            course = course,
            author = author,
            status = status,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    fun buildNewTopicDto(
        title: String = "Error in Kotlin code",
        message: String = "Var x is not defined in the current scope",
        courseId: Long? = 1L,
        authorId: Long? = 1L
    ) = NewTopicDto(
        title = title,
        message = message,
        courseId = courseId,
        authorId = authorId
    )

    fun buildUpdateTopicDto(
        title: String = "Updated title",
        message: String = "Updated message"
    ) = UpdateTopicDto(
        title = title,
        message = message
    )
}