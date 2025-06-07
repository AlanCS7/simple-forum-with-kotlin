package dev.alancss.forum.service

import dev.alancss.forum.dto.NewTopicRequest
import dev.alancss.forum.dto.TopicByCategoryResponse
import dev.alancss.forum.dto.TopicResponse
import dev.alancss.forum.dto.UpdateTopicRequest
import dev.alancss.forum.exception.ResourceNotFoundException
import dev.alancss.forum.mapper.TopicMapper
import dev.alancss.forum.model.Topic
import dev.alancss.forum.repository.TopicRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TopicService(
    private val topicRepository: TopicRepository,
    private val courseService: CourseService,
    private val userService: UserService,
    private val topicMapper: TopicMapper
) {

    fun getAllTopics(courseName: String?, pageable: Pageable): Page<TopicResponse> {
        val topics =
            courseName?.let { topicRepository.findByCourseName(it, pageable) } ?: topicRepository.findAll(pageable)

        return topics.map { topicMapper.toResponseDto(it) }
    }

    fun getById(id: Long): TopicResponse =
        findTopicById(id).let(topicMapper::toResponseDto)

    fun create(dto: NewTopicRequest) {
        val course = courseService.getById(dto.courseId!!)
        val author = userService.getById(dto.authorId!!)
        val topic = topicMapper.toTopic(dto, course, author)
        topicRepository.save(topic)
    }

    fun update(id: Long, dto: UpdateTopicRequest): TopicResponse {
        val topic = findTopicById(id).apply {
            title = dto.title
            message = dto.message
        }
        return topicRepository.save(topic).let(topicMapper::toResponseDto)
    }

    fun delete(id: Long) {
        val topic = findTopicById(id)
        topicRepository.delete(topic)
    }

    private fun findTopicById(id: Long): Topic =
        topicRepository.findByIdOrNull(id)
            ?: throw ResourceNotFoundException("Topic with id $id not found")

    fun report(): List<TopicByCategoryResponse> = topicRepository.report()
}