package dev.alancss.forum.controller

import com.fasterxml.jackson.databind.ObjectMapper
import dev.alancss.forum.config.AbstractTestcontainerConfig
import dev.alancss.forum.dto.NewTopicRequest
import dev.alancss.forum.dto.TopicResponse
import dev.alancss.forum.enum.TopicStatus
import dev.alancss.forum.exception.ResourceNotFoundException
import dev.alancss.forum.security.jwt.JwtUtil
import dev.alancss.forum.service.TopicService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TopicControllerTest : AbstractTestcontainerConfig() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var jwtUtil: JwtUtil

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockitoBean
    private lateinit var topicService: TopicService

    @Test
    fun `should return 403 when no authorization header provided`() {
        mockMvc
            .get(TOPIC_URL)
            .andExpect {
                status { isForbidden() }
            }
    }

    @Test
    fun `should return 200 when valid authorization header provided`() {
        mockMvc
            .get(TOPIC_URL) {
                headers { setBearerAuth(generateToken()) }
            }
            .andExpect {
                status { isOk() }
            }
    }

    @Test
    fun `should return 201 when a new topic is created`() {
        val request = NewTopicRequest(
            title = "New test",
            message = "New test message",
            courseId = 1L,
            authorId = 1L
        )

        mockMvc
            .post(TOPIC_URL) {
                headers { setBearerAuth(generateToken()) }
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(request)
            }
            .andExpect {
                status { isCreated() }
            }
    }

    @Test
    fun `should return 200 when topics are fetched`() {
        val dateTime = LocalDateTime.now()
        val formattedDate = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        val topicResponse = TopicResponse(
            id = 1L,
            title = "Test Topic",
            message = "This is a test topic message",
            status = TopicStatus.NOT_ANSWERED,
            createdAt = dateTime,
            updatedAt = dateTime
        )

        val pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "createdAt"))
        val page = PageImpl(listOf(topicResponse), pageable, 1)

        `when`(topicService.getAllTopics(null, pageable))
            .thenReturn(page)

        mockMvc
            .get(TOPIC_URL) {
                headers { setBearerAuth(generateToken()) }
            }
            .andExpect {
                status { isOk() }
            }
            .andExpect {
                jsonPath("$.content.size()") { value(1) }
                jsonPath("$.content[0].id") { value(1) }
                jsonPath("$.content[0].title") { value("Test Topic") }
                jsonPath("$.content[0].message") { value("This is a test topic message") }
                jsonPath("$.content[0].status") { value("NOT_ANSWERED") }
                jsonPath("$.content[0].createdAt") { value(formattedDate) }
                jsonPath("$.content[0].updatedAt") { value(formattedDate) }
            }
    }

    @Test
    fun `should return 200 when topic are fetched by id`() {
        val dateTime = LocalDateTime.now()
        val formattedDate = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        val topicId = 1L
        val topicResponse = TopicResponse(
            id = topicId,
            title = "Test Topic",
            message = "This is a test topic message",
            status = TopicStatus.NOT_ANSWERED,
            createdAt = dateTime,
            updatedAt = dateTime
        )

        `when`(topicService.getById(topicId))
            .thenReturn(topicResponse)

        mockMvc
            .get("$TOPIC_URL/$topicId") {
                headers { setBearerAuth(generateToken()) }
            }
            .andExpect {
                status { isOk() }
            }
            .andExpect {
                jsonPath("$.id") { value(1) }
                jsonPath("$.title") { value("Test Topic") }
                jsonPath("$.message") { value("This is a test topic message") }
                jsonPath("$.status") { value("NOT_ANSWERED") }
                jsonPath("$.createdAt") { value(formattedDate) }
                jsonPath("$.updatedAt") { value(formattedDate) }
            }
    }

    @Test
    fun `should return 404 when topic is not found`() {
        val topicId = 999L

        `when`(topicService.getById(topicId))
            .thenThrow(ResourceNotFoundException("Topic with id $topicId not found"))

        mockMvc
            .get("$TOPIC_URL/$topicId") {
                headers { setBearerAuth(generateToken()) }
            }
            .andExpect {
                status { isNotFound() }
            }
            .andExpect {
                jsonPath("$.status") { value(404) }
                jsonPath("$.error") { value("NOT_FOUND") }
                jsonPath("$.message") { value("Topic with id $topicId not found") }
                jsonPath("$.path") { value("$TOPIC_URL/$topicId") }
            }
    }

    private fun generateToken(): String {
        return jwtUtil.generateToken(TEST_EMAIL, listOf("READ_WRITE"))
    }

    companion object {
        private const val TOPIC_URL = "/topics"
        private const val TEST_EMAIL = "alan@email.com"
    }
}