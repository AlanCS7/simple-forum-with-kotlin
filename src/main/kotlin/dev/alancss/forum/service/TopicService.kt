package dev.alancss.forum.service

import dev.alancss.forum.dto.NewTopicRequest
import dev.alancss.forum.dto.TopicByCategoryResponse
import dev.alancss.forum.dto.TopicResponse
import dev.alancss.forum.dto.UpdateTopicRequest
import dev.alancss.forum.exception.ResourceNotFoundException
import dev.alancss.forum.mapper.TopicMapper
import dev.alancss.forum.model.Topic
import dev.alancss.forum.repository.TopicRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TopicService(
    private val topicRepository: TopicRepository,
    private val courseService: CourseService,
    private val userService: UserService,
    private val topicMapper: TopicMapper,
    private val topicCacheService: TopicCacheService
) {

    fun getAllTopics(courseName: String?, pageable: Pageable): Page<TopicResponse> {
        val cachedTopics = topicCacheService.getCachedTopics(courseName, pageable)

        val start = pageable.offset.toInt()
        val end = (start + pageable.pageSize).coerceAtMost(cachedTopics.size)
        val pageItems = if (start <= end) cachedTopics.subList(start, end) else emptyList()

        return PageImpl(pageItems, pageable, cachedTopics.size.toLong())
    }

    fun getById(id: Long): TopicResponse =
        findTopicById(id).let(topicMapper::toResponseDto)

    @CacheEvict(value = ["topics"], allEntries = true)
    fun create(request: NewTopicRequest) {
        val course = courseService.getById(request.courseId!!)
        val author = userService.getById(request.authorId!!)
        val topic = topicMapper.toTopic(request, course, author)
        topicRepository.save(topic)
    }

    @CacheEvict(value = ["topics"], allEntries = true)
    fun update(id: Long, request: UpdateTopicRequest): TopicResponse {
        val topic = findTopicById(id).apply {
            title = request.title
            message = request.message
        }
        return topicRepository.save(topic).let(topicMapper::toResponseDto)
    }

    @CacheEvict(value = ["topics"], allEntries = true)
    fun delete(id: Long) {
        val topic = findTopicById(id)
        topicRepository.delete(topic)
    }

    private fun findTopicById(id: Long): Topic =
        topicRepository.findByIdOrNull(id)
            ?: throw ResourceNotFoundException("Topic with id $id not found")

    fun report(): List<TopicByCategoryResponse> = topicRepository.report()
}