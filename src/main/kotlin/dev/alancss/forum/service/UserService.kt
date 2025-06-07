package dev.alancss.forum.service

import dev.alancss.forum.dto.AuthRegisterRequest
import dev.alancss.forum.enum.RoleType
import dev.alancss.forum.exception.BusinessException
import dev.alancss.forum.exception.ResourceNotFoundException
import dev.alancss.forum.model.User
import dev.alancss.forum.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val roleService: RoleService
) {

    companion object {
        private val DEFAULT_ROLE = RoleType.READ_ONLY.name
    }

    fun getById(id: Long): User =
        userRepository.findByIdOrNull(id)
            ?: throw ResourceNotFoundException("Author with id $id not found")

    fun existsByEmail(email: String): Boolean =
        userRepository.existsByEmail(email)

    fun registerNewUser(request: AuthRegisterRequest) {
        if (existsByEmail(request.email))
            throw BusinessException("Email is already in use")

        val role = roleService.getByName(DEFAULT_ROLE)

        val passwordEncoded = passwordEncoder.encode(request.password)
        val user = User(
            name = request.name,
            email = request.email,
            password = passwordEncoded,
            roles = listOf(role)
        )

        userRepository.save(user)
    }
}
