package dev.alancss.forum.security

import dev.alancss.forum.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails? {
        return userRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("User with username $username not found")
    }
}