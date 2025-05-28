package dev.alancss.forum.factory

import dev.alancss.forum.model.Course

object CourseTestFactory {

    fun build(
        id: Long? = 1L,
        name: String = "Kotlin",
        category: String = "Programming"
    ): Course {
        return Course(
            id = id,
            name = name,
            category = category
        )
    }
}