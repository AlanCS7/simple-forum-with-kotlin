package dev.alancss.forum.security.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*


@Component
class JWTUtil {

    private val expiration: Long = 1000 * 60 * 60 // 1 hour

    @Value("\${jwt.secret-key}")
    private lateinit var secretKey: String

    fun generateToken(username: String, authorities: List<String>): String {
        return Jwts.builder()
            .subject(username)
            .claim("authorities", authorities)
            .issuedAt(Date.from(Instant.now()))
            .expiration(Date.from(Instant.now().plusMillis(expiration)))
            .signWith(Keys.hmacShaKeyFor(secretKey.toByteArray()), Jwts.SIG.HS512)
            .compact()
    }

}