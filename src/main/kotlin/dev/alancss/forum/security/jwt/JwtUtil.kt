package dev.alancss.forum.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*
import javax.crypto.SecretKey


@Component
class JwtUtil(
    @Value("\${jwt.secret-key}")
    private val secretKey: String
) {

    private val expiration: Long = 1000 * 60 * 60 // 1 hour

    fun generateToken(username: String, authorities: List<String>): String {
        val now = Instant.now()
        val expirationTime = now.plusMillis(expiration)

        return Jwts.builder()
            .subject(username)
            .claim("authorities", authorities)
            .issuer("forum-api")
            .audience().add("forum-client").and()
            .issuedAt(Date.from(now))
            .notBefore(Date.from(now))
            .expiration(Date.from(expirationTime))
            .id(UUID.randomUUID().toString())
            .signWith(getSigningKey(), Jwts.SIG.HS512)
            .compact()
    }

    fun getSubject(token: String): String? = getClaims(token).subject

    fun getExpiration(token: String): Long = getClaims(token).expiration.time

    fun isTokenValid(token: String, username: String): Boolean =
        getSubject(token) == username && !isTokenExpired(token)

    private fun isTokenExpired(token: String): Boolean =
        getClaims(token).expiration.before(Date.from(Instant.now()))

    private fun getClaims(token: String): Claims = Jwts.parser()
        .verifyWith(getSigningKey())
        .build()
        .parseSignedClaims(token)
        .payload

    private fun getSigningKey(): SecretKey = Keys.hmacShaKeyFor(secretKey.toByteArray())
}