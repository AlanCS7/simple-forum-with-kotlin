package dev.alancss.forum.repository

import dev.alancss.forum.factory.CourseTestFactory
import dev.alancss.forum.factory.TopicTestFactory
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import kotlin.test.assertEquals

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TopicRepositoryTest {

    @Autowired
    private lateinit var topicRepository: TopicRepository

    @Autowired
    lateinit var courseRepository: CourseRepository

    companion object {
        private const val POSTGRES_IMAGE = "postgres:latest"
        private const val DB_NAME = "forum-testdb"
        private const val DB_USERNAME = "username"
        private const val DB_PASSWORD = "password"

        @JvmField
        @Container
        val postgresqlContainer = PostgreSQLContainer<Nothing>(POSTGRES_IMAGE).apply {
            withDatabaseName(DB_NAME)
            withUsername(DB_USERNAME)
            withPassword(DB_PASSWORD)
        }

        @JvmStatic
        @DynamicPropertySource
        fun configureProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgresqlContainer::getUsername)
            registry.add("spring.datasource.password", postgresqlContainer::getPassword)
        }
    }

    @BeforeEach
    fun setUp() {
        val course = courseRepository.save(CourseTestFactory.build(id = null))
        val topic = TopicTestFactory.build(id = null, course = course)
        topicRepository.save(topic)
    }

    @AfterEach
    fun tearDown() {
        topicRepository.deleteAll()
        courseRepository.deleteAll()
    }

    @Test
    fun `should load context and repository`() {
        assertNotNull(topicRepository)
    }

    @Test
    fun `should return topic by course name`() {
        val result = topicRepository.findByCourseName(courseName = "Kotlin")
        val topic = result.first()

        assertNotNull(result)
        assertEquals(1, result.size)

        assertEquals("Kotlin", topic.course.name)
        assertEquals("Error in Kotlin code", topic.title)
        assertEquals("Var x is not defined in the current scope", topic.message)
    }

    @Test
    fun `should generate report`() {
        val report = topicRepository.report()

        assertNotNull(report)

        val dto = report.first()
        assertEquals("Programming", dto.category)
        assertEquals(1, dto.quantity)
    }
}