package dev.alancss.forum.service

import dev.alancss.forum.exception.ResourceNotFoundException
import dev.alancss.forum.factory.CourseTestFactory
import dev.alancss.forum.factory.TopicByCategoryFactory
import dev.alancss.forum.factory.TopicTestFactory
import dev.alancss.forum.factory.UserTestFactory
import dev.alancss.forum.mapper.TopicMapper
import dev.alancss.forum.mapper.TopicMapperTestFactory
import dev.alancss.forum.repository.TopicRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime
import java.util.*

class TopicServiceTest {
    private val topicRepository = mockk<TopicRepository>()
    private val courseService = mockk<CourseService>()
    private val userService = mockk<UserService>()
    private val topicMapper = mockk<TopicMapper>()
    private val topicCacheService = mockk<TopicCacheService>()

    val topicService = TopicService(topicRepository, courseService, userService, topicMapper, topicCacheService)

    @Test
    fun `should return topics by course name`() {
        val courseName = "Kotlin"
        val topicResponseDto = TopicMapperTestFactory.toResponseDto()

        val pageable = mockk<Pageable>()
        every { pageable.offset } returns 0L
        every { pageable.pageSize } returns 10
        every { pageable.pageNumber } returns 0
        every { pageable.toOptional() } returns Optional.of(pageable)

        every { topicCacheService.getCachedTopics(courseName, pageable) } returns listOf(topicResponseDto)

        val result = topicService.getAllTopics(courseName, pageable)

        assertEquals(1, result.content.size)
        assertEquals(topicResponseDto, result.content.first())

        verify(exactly = 1) { topicCacheService.getCachedTopics(courseName, pageable) }
    }

    @Test
    fun `should return all topics when course name is null`() {
        val topicResponseDto = TopicMapperTestFactory.toResponseDto()

        val pageable = mockk<Pageable>()
        every { pageable.offset } returns 0L
        every { pageable.pageSize } returns 10
        every { pageable.pageNumber } returns 0
        every { pageable.toOptional() } returns Optional.of(pageable)

        every { topicCacheService.getCachedTopics(null, pageable) } returns listOf(topicResponseDto)

        val result = topicService.getAllTopics(null, pageable)

        assertEquals(1, result.content.size)
        verify(exactly = 1) { topicCacheService.getCachedTopics(null, pageable) }
    }

    @Test
    fun `should return empty page when no topics found by course name`() {
        val courseName = "Nonexistent Course"

        val pageable = mockk<Pageable>()
        every { pageable.offset } returns 0L
        every { pageable.pageSize } returns 10
        every { pageable.pageNumber } returns 0
        every { pageable.toOptional() } returns Optional.of(pageable)

        every { topicCacheService.getCachedTopics(courseName, pageable) } returns emptyList()

        val result = topicService.getAllTopics(courseName, pageable)

        assertEquals(0, result.content.size)
        verify(exactly = 1) { topicCacheService.getCachedTopics(courseName, pageable) }
    }

    @Test
    fun `should return empty page when no topics found and course name is null`() {
        val pageable = mockk<Pageable>()
        every { pageable.offset } returns 0L
        every { pageable.pageSize } returns 10
        every { pageable.pageNumber } returns 0
        every { pageable.toOptional() } returns Optional.of(pageable)

        every { topicCacheService.getCachedTopics(null, pageable) } returns emptyList()

        val result = topicService.getAllTopics(null, pageable)

        assertEquals(0, result.content.size)

        verify(exactly = 1) { topicCacheService.getCachedTopics(null, pageable) }
    }

    @Test
    fun `should return topic by id`() {
        val topicId = 1L
        val topic = TopicTestFactory.build(id = topicId)
        val topicResponseDto = TopicMapperTestFactory.toResponseDto()

        every { topicRepository.findByIdOrNull(topicId) } returns topic
        every { topicMapper.toResponseDto(topic) } returns topicResponseDto

        val result = topicService.getById(topicId)

        assertEquals(topicResponseDto, result)
        verify(exactly = 1) { topicRepository.findById(topicId) }
        verify(exactly = 1) { topicMapper.toResponseDto(any()) }
    }

    @Test
    fun `should throw ResourceNotFoundException when topic not found`() {
        val topicId = 999L

        every { topicRepository.findByIdOrNull(topicId) } returns null

        val exception = assertThrows<ResourceNotFoundException> {
            topicService.getById(topicId)
        }

        assertEquals("Topic with id $topicId not found", exception.message)
        verify(exactly = 1) { topicRepository.findByIdOrNull(topicId) }
        verify(exactly = 0) { topicMapper.toResponseDto(any()) }
    }

    @Test
    fun `should create a new topic`() {
        val newTopicDto = TopicTestFactory.buildNewTopicDto()
        val course = CourseTestFactory.build(id = newTopicDto.courseId)
        val author = UserTestFactory.build(id = newTopicDto.authorId)

        val topic = TopicTestFactory.build(
            title = newTopicDto.title,
            message = newTopicDto.message,
            course = course,
            author = author
        )

        every { courseService.getById(newTopicDto.courseId!!) } returns course
        every { userService.getById(newTopicDto.authorId!!) } returns author
        every { topicMapper.toTopic(request = newTopicDto, course = course, author = author) } returns topic
        every { topicRepository.save(topic) } returns topic

        topicService.create(newTopicDto)

        verify(exactly = 1) { courseService.getById(newTopicDto.courseId!!) }
        verify(exactly = 1) { userService.getById(newTopicDto.authorId!!) }
        verify(exactly = 1) { topicMapper.toTopic(request = newTopicDto, course = course, author = author) }
        verify(exactly = 1) { topicRepository.save(topic) }
    }

    @Test
    fun `should throw ResourceNotFoundException when creating topic with non-existent course`() {
        val newTopicDto = TopicTestFactory.buildNewTopicDto(courseId = 999L)

        every { courseService.getById(newTopicDto.courseId!!) } throws ResourceNotFoundException("Course with id ${newTopicDto.courseId} not found")

        val exception = assertThrows<ResourceNotFoundException> {
            topicService.create(newTopicDto)
        }

        assertEquals("Course with id ${newTopicDto.courseId} not found", exception.message)
        verify(exactly = 1) { courseService.getById(newTopicDto.courseId!!) }
        verify(exactly = 0) { userService.getById(newTopicDto.authorId!!) }
        verify(exactly = 0) { topicMapper.toTopic(request = newTopicDto, course = any(), author = any()) }
        verify(exactly = 0) { topicRepository.save(any()) }
    }

    @Test
    fun `should throw ResourceNotFoundException when creating topic with non-existent author`() {
        val newTopicDto = TopicTestFactory.buildNewTopicDto(authorId = 999L)
        val course = CourseTestFactory.build(id = newTopicDto.courseId)

        every { courseService.getById(newTopicDto.courseId!!) } returns course
        every { userService.getById(newTopicDto.authorId!!) } throws ResourceNotFoundException("Author with id ${newTopicDto.authorId} not found")

        val exception = assertThrows<ResourceNotFoundException> {
            topicService.create(newTopicDto)
        }

        assertEquals("Author with id ${newTopicDto.authorId} not found", exception.message)
        verify(exactly = 1) { courseService.getById(newTopicDto.courseId!!) }
        verify(exactly = 1) { userService.getById(newTopicDto.authorId!!) }
        verify(exactly = 0) { topicMapper.toTopic(request = newTopicDto, course = course, author = any()) }
        verify(exactly = 0) { topicRepository.save(any()) }
    }

    @Test
    fun `should update topic by id`() {
        val topicId = 1L
        val existingTopic = TopicTestFactory.build(id = topicId)
        val updateTopicDto = TopicTestFactory.buildUpdateTopicDto()

        val updatedAt = LocalDateTime.now()
        val mutatedTopic = TopicTestFactory.build(
            id = topicId,
            title = updateTopicDto.title,
            message = updateTopicDto.message,
            updatedAt = updatedAt
        )

        val toResponseDto =
            TopicMapperTestFactory.toResponseDto(
                title = mutatedTopic.title,
                message = mutatedTopic.message,
                updatedAt = updatedAt
            )

        every { topicRepository.findByIdOrNull(topicId) } returns existingTopic
        every { topicRepository.save(existingTopic) } returns mutatedTopic
        every { topicMapper.toResponseDto(mutatedTopic) } returns toResponseDto

        val result = topicService.update(topicId, updateTopicDto)

        assertEquals(toResponseDto, result)
        verify(exactly = 1) { topicRepository.findByIdOrNull(topicId) }
        verify(exactly = 1) { topicRepository.save(existingTopic) }
        verify(exactly = 1) { topicMapper.toResponseDto(mutatedTopic) }
    }

    @Test
    fun `should throw ResourceNotFoundException when updating non-existent topic`() {
        val topicId = 999L
        val updateTopicDto = TopicTestFactory.buildUpdateTopicDto()

        every { topicRepository.findByIdOrNull(topicId) } returns null

        val exception = assertThrows<ResourceNotFoundException> {
            topicService.update(topicId, updateTopicDto)
        }

        assertEquals("Topic with id $topicId not found", exception.message)
        verify(exactly = 1) { topicRepository.findByIdOrNull(topicId) }
        verify(exactly = 0) { topicRepository.save(any()) }
        verify(exactly = 0) { topicMapper.toResponseDto(any()) }
    }

    @Test
    fun `should delete topic by id`() {
        val topicId = 1L
        val topic = TopicTestFactory.build(id = topicId)

        every { topicRepository.findByIdOrNull(topicId) } returns topic
        every { topicRepository.delete(topic) } returns Unit

        topicService.delete(topicId)

        verify(exactly = 1) { topicRepository.findByIdOrNull(topicId) }
        verify(exactly = 1) { topicRepository.delete(topic) }
    }

    @Test
    fun `should throw ResourceNotFoundException when deleting non-existent topic`() {
        val topicId = 999L
        every { topicRepository.findByIdOrNull(topicId) } returns null

        val exception = assertThrows<ResourceNotFoundException> {
            topicService.delete(topicId)
        }

        assertEquals("Topic with id $topicId not found", exception.message)
        verify(exactly = 1) { topicRepository.findByIdOrNull(topicId) }
        verify(exactly = 0) { topicRepository.delete(any()) }
    }

    @Test
    fun `should return report of topics by category`() {
        val topicByCategoryDto = TopicByCategoryFactory.build()
        val report = listOf(topicByCategoryDto)

        every { topicRepository.report() } returns report

        val result = topicService.report()

        assertEquals(report, result)
        verify(exactly = 1) { topicRepository.report() }
    }
}
