package dev.alancss.forum.dto

import jakarta.validation.constraints.NotBlank

data class AuthRequest(
    @NotBlank(message = "Username is required")
    val username: String,
    @NotBlank(message = "Password is required")
    val password: String
)