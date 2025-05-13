package dev.alancss.forum.dto

data class AuthResponse(
    val token: String,
    val expiresIn: Long,
    val tokenType: String
)