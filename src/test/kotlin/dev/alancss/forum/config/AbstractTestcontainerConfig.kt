package dev.alancss.forum.config

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
abstract class AbstractTestcontainerConfig {

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

        @Container
        private val redisContainer = GenericContainer<Nothing>("redis:latest").apply {
            withExposedPorts(6379)
        }

        @JvmStatic
        @DynamicPropertySource
        fun configureProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgresqlContainer::getUsername)
            registry.add("spring.datasource.password", postgresqlContainer::getPassword)

            registry.add("spring.data.redis.host", redisContainer::getHost)
            registry.add("spring.data.redis.port", redisContainer::getFirstMappedPort)
        }
    }
}