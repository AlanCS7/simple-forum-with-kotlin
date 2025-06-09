package dev.alancss.forum.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import java.time.Duration

@EnableCaching
@Configuration
class RedisConfig {

    @Value("\${cache.ttl}")
    private lateinit var cacheTtl: String

    @Bean
    fun redisTemplate(factory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.connectionFactory = factory
        template.valueSerializer = GenericJackson2JsonRedisSerializer()
        return template
    }

    @Bean
    fun redisCacheConfiguration(): RedisCacheConfiguration {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule()) // Register JavaTimeModule for date/time serialization
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

        return RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(cacheTtl.toLong()))
            .disableCachingNullValues()
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    GenericJackson2JsonRedisSerializer(objectMapper)
                )
            )
    }
}