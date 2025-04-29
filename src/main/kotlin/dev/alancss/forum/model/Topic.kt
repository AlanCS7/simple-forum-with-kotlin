package dev.alancss.forum.model

import dev.alancss.forum.enum.TopicStatus
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import java.time.LocalDateTime

@Entity
class Topic(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var title: String,
    var message: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @ManyToOne
    val course: Course,
    @ManyToOne
    val author: User,
    @Enumerated(EnumType.STRING)
    val status: TopicStatus = TopicStatus.NOT_ANSWERED,
    @OneToMany(mappedBy = "topic")
    val answers: List<Answer> = ArrayList()
)