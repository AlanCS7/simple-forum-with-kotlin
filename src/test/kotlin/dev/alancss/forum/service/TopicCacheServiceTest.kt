package dev.alancss.forum.service

import dev.alancss.forum.factory.TopicTestFactory
import dev.alancss.forum.mapper.TopicMapper
import dev.alancss.forum.mapper.TopicMapperTestFactory
import dev.alancss.forum.model.Topic
import dev.alancss.forum.repository.TopicRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

class TopicCacheServiceTest {

    private val topicRepository = mockk<TopicRepository>()
    private val topicMapper = mockk<TopicMapper>()
    private val topicCacheService = TopicCacheService(topicRepository, topicMapper)

    @Test
    fun `should return mapped topics by course name`() {
        val courseName = "Kotlin"
        val topic = TopicTestFactory.build()
        val topicResponse = TopicMapperTestFactory.toResponseDto()

        every { topicRepository.findByCourseName(courseName) } returns listOf(topic)
        every { topicMapper.toResponseDto(topic) } returns topicResponse

        val result = topicCacheService.getCachedTopics(courseName, mockk())

        assertEquals(1, result.size)
        assertEquals(topicResponse, result[0])

        verify(exactly = 1) { topicRepository.findByCourseName(courseName) }
        verify(exactly = 1) { topicMapper.toResponseDto(topic) }
    }

    @Test
    fun `should return mapped topics when course name is null`() {
        val pageable = mockk<Pageable>()
        val topic = TopicTestFactory.build()
        val topicResponse = TopicMapperTestFactory.toResponseDto()

        val page: Page<Topic> = PageImpl(listOf(topic))

        every { topicRepository.findAll(pageable) } returns page
        every { topicMapper.toResponseDto(topic) } returns topicResponse

        val result = topicCacheService.getCachedTopics(null, pageable)

        assertEquals(1, result.size)
        assertEquals(topicResponse, result[0])

        verify(exactly = 1) { topicRepository.findAll(pageable) }
        verify(exactly = 1) { topicMapper.toResponseDto(topic) }
    }

    @Test
    fun `should return empty list when no topics found by course name`() {
        val courseName = "Nonexistent Course"

        every { topicRepository.findByCourseName(courseName) } returns emptyList()

        val result = topicCacheService.getCachedTopics(courseName, mockk())

        assertEquals(0, result.size)

        verify(exactly = 1) { topicRepository.findByCourseName(courseName) }
        verify(exactly = 0) { topicMapper.toResponseDto(any()) }
    }

    @Test
    fun `should return empty list when no topics found and course name is null`() {
        val pageable = mockk<Pageable>()

        every { topicRepository.findAll(pageable) } returns PageImpl(emptyList())

        val result = topicCacheService.getCachedTopics(null, pageable)

        assertEquals(0, result.size)

        verify(exactly = 1) { topicRepository.findAll(pageable) }
        verify(exactly = 0) { topicMapper.toResponseDto(any()) }
    }
}