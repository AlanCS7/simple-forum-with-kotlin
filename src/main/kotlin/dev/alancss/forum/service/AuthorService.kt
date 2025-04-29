package dev.alancss.forum.service

import dev.alancss.forum.exception.ResourceNotFoundException
import dev.alancss.forum.model.User
import dev.alancss.forum.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AuthorService(
    private val userRepository: UserRepository,
) {

    fun getById(id: Long): User =
        userRepository.findByIdOrNull(id)
            ?: throw ResourceNotFoundException("Author with id $id not found")

}
