package dev.alancss.forum.controller

import dev.alancss.forum.dto.NewTopicDto
import dev.alancss.forum.dto.TopicResponseDto
import dev.alancss.forum.dto.UpdateTopicDto
import dev.alancss.forum.service.TopicService
import jakarta.validation.Valid
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/topics")
class TopicController(private val service: TopicService) {

    @GetMapping
    @Cacheable("topics")
    fun getAll(
        @RequestParam("courseName", required = false) courseName: String?,
        @PageableDefault(size = 5, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable
    ): Page<TopicResponseDto> =
        service.getAllTopics(courseName, pageable)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): TopicResponseDto = service.getById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = ["topics"], allEntries = true)
    fun create(@RequestBody @Valid dto: NewTopicDto) = service.create(dto)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = ["topics"], allEntries = true)
    fun update(@PathVariable id: Long, @RequestBody @Valid dto: UpdateTopicDto) = service.update(id, dto)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = ["topics"], allEntries = true)
    fun delete(@PathVariable id: Long) = service.delete(id)
}