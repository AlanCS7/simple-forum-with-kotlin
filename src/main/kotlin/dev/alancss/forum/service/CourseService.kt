package dev.alancss.forum.service

import dev.alancss.forum.exception.ResourceNotFoundException
import dev.alancss.forum.model.Course
import dev.alancss.forum.repository.CourseRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CourseService(
    private val courseRepository: CourseRepository
) {

    fun getById(id: Long): Course =
        courseRepository.findByIdOrNull(id)
            ?: throw ResourceNotFoundException("Course with id $id not found")

}
