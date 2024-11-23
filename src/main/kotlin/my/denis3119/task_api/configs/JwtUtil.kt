package my.denis3119.task_api.configs

import io.jsonwebtoken.Jwts
import java.util.*
import java.util.concurrent.TimeUnit

object JwtUtil {

    private val key = Jwts.SIG.HS256.key().build()
    private val expirationTime = TimeUnit.DAYS.toMillis(100) // 100 days for testing

    fun generateToken(username: String): String {
        return Jwts.builder()
            .subject(username)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + expirationTime))
            .signWith(key)
            .compact()
    }

    fun extractUsername(token: String): String? {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).payload.subject
    }

    fun isTokenValid(token: String, username: String): Boolean {
        return extractUsername(token) == username
    }
}
