package dev.alancss.forum.model

import dev.alancss.forum.enum.TopicStatus
import dev.alancss.forum.factory.CourseTestFactory
import dev.alancss.forum.factory.UserTestFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class TopicTest {

    @Test
    fun `should create a topic with valid parameters`() {
        val createdAt = LocalDateTime.now()
        val updatedAt = LocalDateTime.now().plusMinutes(5)

        val topic = Topic(
            id = 1L,
            title = "Test Title",
            message = "Test Message",
            createdAt = createdAt,
            course = CourseTestFactory.build(),
            author = UserTestFactory.build(),
            status = TopicStatus.NOT_ANSWERED,
            answers = emptyList(),
            updatedAt = updatedAt
        )

        assertNotNull(topic)
        assertEquals(1L, topic.id)
        assertEquals("Test Title", topic.title)
        assertEquals("Test Message", topic.message)
        assertEquals(createdAt, topic.createdAt)
        assertEquals("Test Course", topic.course.name)
        assertEquals("Test User", topic.author.name)
        assertEquals(TopicStatus.NOT_ANSWERED, topic.status)
        assertEquals(0, topic.answers.size)
        assertEquals(updatedAt, topic.updatedAt)

    }

}