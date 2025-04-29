package dev.alancss.forum.service

import dev.alancss.forum.dto.NewTopicDto
import dev.alancss.forum.dto.TopicResponseDto
import dev.alancss.forum.dto.UpdateTopicDto
import dev.alancss.forum.exception.ResourceNotFoundException
import dev.alancss.forum.mapper.TopicMapper
import dev.alancss.forum.model.Topic
import dev.alancss.forum.repository.TopicRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TopicService(
    private val topicRepository: TopicRepository,
    private val courseService: CourseService,
    private val authorService: AuthorService,
    private val topicMapper: TopicMapper
) {

    fun getAllTopics(): List<TopicResponseDto> =
        topicRepository.findAll()
            .map { topicMapper.toResponseDto(it) }

    fun getById(id: Long): TopicResponseDto =
        findTopicById(id).let(topicMapper::toResponseDto)

    fun create(dto: NewTopicDto) {
        val course = courseService.getById(dto.courseId!!)
        val author = authorService.getById(dto.authorId!!)
        val topic = topicMapper.toTopic(dto, course, author)
        topicRepository.save(topic)
    }

    fun update(id: Long, dto: UpdateTopicDto): TopicResponseDto {
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
}