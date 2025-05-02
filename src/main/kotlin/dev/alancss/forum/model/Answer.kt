package dev.alancss.forum.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
class Answer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "message", nullable = false)
    val message: String,

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    val createdAt: LocalDateTime? = null,

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    val author: User,

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    val topic: Topic,

    @Column(name = "solution", nullable = false)
    val solution: Boolean = false
)
