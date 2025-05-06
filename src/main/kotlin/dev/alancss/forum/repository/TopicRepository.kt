package dev.alancss.forum.repository

import dev.alancss.forum.model.Topic
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface TopicRepository : JpaRepository<Topic, Long> {

    fun findByCourseName(courseName: String, pageable: Pageable): Page<Topic>
}