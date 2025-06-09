package dev.alancss.forum.controller

import dev.alancss.forum.dto.NewAnswerRequest
import dev.alancss.forum.service.AnswerService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/answers")
class AnswerController(
    private val answerService: AnswerService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid request: NewAnswerRequest) {
        answerService.create(request)
    }
}