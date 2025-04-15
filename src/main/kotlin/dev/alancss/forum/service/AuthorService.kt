package dev.alancss.forum.service

import dev.alancss.forum.exception.ResourceNotFoundException
import dev.alancss.forum.model.User
import org.springframework.stereotype.Service

@Service
class AuthorService {

    private val authors = mutableListOf<User>()

    init {
        initializeCourses()
    }

    fun getById(id: Long): User =
        authors.firstOrNull { it.id == id } ?: throw ResourceNotFoundException("Author with id $id not found")

    private fun initializeCourses() {
        authors.addAll(
            listOf(
                User(id = 1, name = "Alan Silva", email = "alancss@email.com"),
                User(id = 1, name = "Kathlyn da Silva Bergamo", email = "kathlyn@email.com"),
                User(id = 1, name = "Luan Silva", email = "luan@email.com")
            )
        )
    }
}
