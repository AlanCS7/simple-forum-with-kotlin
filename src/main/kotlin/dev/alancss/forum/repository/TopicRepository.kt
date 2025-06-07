package dev.alancss.forum.repository

import dev.alancss.forum.dto.TopicByCategoryResponse
import dev.alancss.forum.model.Topic
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TopicRepository : JpaRepository<Topic, Long> {

    fun findByCourseName(courseName: String, pageable: Pageable): Page<Topic>

    @Query(
        """
        SELECT new dev.alancss.forum.dto.TopicByCategoryResponse(
            c.category,
            COUNT(t)
        )
        FROM Topic t
        JOIN t.course c
        GROUP BY c.category
    """
    )
    fun report(): List<TopicByCategoryResponse>
}