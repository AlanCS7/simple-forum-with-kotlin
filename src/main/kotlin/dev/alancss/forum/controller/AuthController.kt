package dev.alancss.forum.controller

import dev.alancss.forum.dto.AuthLoginRequest
import dev.alancss.forum.dto.AuthRegisterRequest
import dev.alancss.forum.dto.AuthResponse
import dev.alancss.forum.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody @Valid request: AuthRegisterRequest) {
        authService.register(request)
    }

    @PostMapping("/login")
    fun login(@RequestBody request: AuthLoginRequest): ResponseEntity<AuthResponse> {
        val response = authService.login(request)
        return ResponseEntity.ok(response)
    }
}