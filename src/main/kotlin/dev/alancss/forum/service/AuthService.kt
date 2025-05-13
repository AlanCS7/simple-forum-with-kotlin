package dev.alancss.forum.service

import dev.alancss.forum.dto.AuthRequest
import dev.alancss.forum.dto.AuthResponse
import dev.alancss.forum.model.User
import dev.alancss.forum.security.jwt.JwtUtil
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val jwtUtil: JwtUtil
) {

    fun login(authRequest: AuthRequest): AuthResponse {
        val auth = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authRequest.username,
                authRequest.password
            )
        )

        val user = auth.principal as User
        val token = jwtUtil.generateToken(user.email, user.roles.map { it.name })
        val expiresIn = (jwtUtil.getExpiration(token) - System.currentTimeMillis()) / 1000

        return AuthResponse(token, expiresIn, "Bearer")
    }

}
