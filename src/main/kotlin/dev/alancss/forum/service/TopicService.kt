package dev.alancss.forum.service

import dev.alancss.forum.dto.NewTopicDto
import dev.alancss.forum.dto.TopicResponseDto
import dev.alancss.forum.dto.UpdateTopicDto
import dev.alancss.forum.exception.ResourceNotFoundException
import dev.alancss.forum.mapper.TopicMapper
import dev.alancss.forum.model.Topic
import org.springframework.stereotype.Service

@Service
class TopicService(
    private val courseService: CourseService,
    private val authorService: AuthorService,
    private val topicMapper: TopicMapper
) {

    private val topics = mutableListOf<Topic>()
    private var currentId = 0L

    fun getAllTopics(): List<TopicResponseDto> = topics.map { topicMapper.toResponseDto(it) }

    fun getById(id: Long): TopicResponseDto =
        topics.firstOrNull { it.id == id }?.let { topicMapper.toResponseDto(it) }
            ?: throw ResourceNotFoundException("Topic with id $id not found")

    fun create(dto: NewTopicDto) {
        val course = courseService.getById(dto.courseId)
        val author = authorService.getById(dto.authorId)
        val topic = topicMapper.toTopic(dto, course, author).copy(id = ++currentId)
        topics.add(topic)
    }

    fun update(id: Long, dto: UpdateTopicDto) =
        topics.firstOrNull { it.id == id }?.let { topic ->
            topic.title = dto.title
            topic.message = dto.message
        } ?: throw ResourceNotFoundException("Topic with id $id not found")

    fun delete(id: Long) {
        val topic = topics.firstOrNull { it.id == id }
            ?: throw ResourceNotFoundException("Topic with id $id not found")
        topics.remove(topic)
    }

}