package dev.alancss.forum.service

import dev.alancss.forum.exception.ResourceNotFoundException
import dev.alancss.forum.model.Role
import dev.alancss.forum.repository.RoleRepository
import org.springframework.stereotype.Service

@Service
class RoleService(
    private val roleRepository: RoleRepository
) {
    fun getByName(name: String): Role = roleRepository.findByName(name)
        ?: throw ResourceNotFoundException("Role with name $name not found")
}
