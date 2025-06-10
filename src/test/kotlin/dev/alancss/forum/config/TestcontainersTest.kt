package dev.alancss.forum.config

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TestcontainersTest : AbstractTestcontainerConfig() {

    @Test
    fun canStartPostgresDB() {
        assertThat(postgresqlContainer.isCreated).isTrue()
        assertThat(postgresqlContainer.isRunning).isTrue()
    }
}