package dev.alancss.forum.repository

import dev.alancss.forum.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : JpaRepository<Role, Int> {
    fun findByName(name: String): Role?
}
