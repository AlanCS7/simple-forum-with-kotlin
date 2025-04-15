package dev.alancss.forum.service

import dev.alancss.forum.exception.ResourceNotFoundException
import dev.alancss.forum.model.Course
import org.springframework.stereotype.Service

@Service
class CourseService {

    private val courses = mutableListOf<Course>()

    init {
        initializeCourses()
    }

    fun getById(id: Long): Course =
        courses.firstOrNull { it.id == id } ?: throw ResourceNotFoundException("Course with id $id not found")

    private fun initializeCourses() {
        val course = Course(
            id = 1,
            name = "Kotlin",
            category = "Programação"
        )

        courses.addAll(
            listOf(
                course,
                course.copy(id = 2, name = "Java"),
                course.copy(id = 3, name = "Spring")
            )
        )
    }
}
