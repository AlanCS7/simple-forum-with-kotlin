package dev.alancss.forum.service

import dev.alancss.forum.dto.TopicResponse
import dev.alancss.forum.mapper.TopicMapper
import dev.alancss.forum.repository.TopicRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TopicCacheService(
    private val topicRepository: TopicRepository,
    private val topicMapper: TopicMapper
) {

    @Cacheable(value = ["topics"], key = "#courseName ?: 'all'")
    fun getCachedTopics(courseName: String?, pageable: Pageable): List<TopicResponse> {
        val topics = courseName?.let {
            topicRepository.findByCourseName(it)
        } ?: topicRepository.findAll(pageable)

        return topics.map { topicMapper.toResponseDto(it) }
    }
}
