package dev.alancss.forum.repository

import dev.alancss.forum.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String?): User?

    fun existsByEmail(email: String): Boolean
}