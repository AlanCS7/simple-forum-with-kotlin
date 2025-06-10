package dev.alancss.forum.service

import dev.alancss.forum.dto.NewAnswerRequest
import dev.alancss.forum.enum.EmailTemplate
import dev.alancss.forum.enum.TopicStatus
import dev.alancss.forum.model.Answer
import dev.alancss.forum.repository.AnswerRepository
import org.springframework.stereotype.Service

@Service
class AnswerService(
    private val answerRepository: AnswerRepository,
    private val authService: AuthService,
    private val topicService: TopicService,
    private val emailService: EmailService
) {

    fun create(request: NewAnswerRequest) {
        val topic = topicService.findTopicById(request.topicId)

        val answer = Answer(
            message = request.message,
            author = authService.getCurrentUser(),
            topic = topic
        )

        answerRepository.save(answer)

        topic.status = TopicStatus.ANSWERED
        topicService.save(topic)

        emailService.sendEmail(
            authorEmail = topic.author.email,
            authorName = topic.author.name,
            template = EmailTemplate.TOPIC_ANSWERED
        )
    }

}