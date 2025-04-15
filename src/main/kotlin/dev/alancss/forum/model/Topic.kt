package dev.alancss.forum.model

import dev.alancss.forum.enum.TopicStatus
import java.time.LocalDateTime

data class Topic(
    val id: Long? = null,
    var title: String,
    var message: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val course: Course,
    val author: User,
    val status: TopicStatus = TopicStatus.NOT_ANSWERED,
    val answers: List<Answer> = ArrayList()
)