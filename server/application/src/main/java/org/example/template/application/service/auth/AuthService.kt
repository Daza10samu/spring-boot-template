package org.example.template.application.service.auth

import org.example.template.application.service.auth.models.JwtAuthentication
import org.example.template.application.service.auth.models.JwtTokens
import org.example.template.domain.model.auth.User
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userService: UserDetailsServiceImpl,
    private val jwtProvider: JwtProvider,
    private val passwordEncoder: PasswordEncoder,
) {
    private val refreshStorage: MutableMap<String, String> = HashMap()

    fun login(username: String, password: String): JwtTokens {
        val user: User = userService.getUser(username)
        return if (passwordEncoder.matches(password, user.password)) {
            val accessToken = jwtProvider.generateAccessToken(user)
            val refreshToken = jwtProvider.generateRefreshToken(user)
            refreshStorage[user.username] = refreshToken
            JwtTokens(accessToken, refreshToken)
        } else {
            throw IllegalArgumentException("Wrong password")
        }
    }

    fun getAccessToken(refreshToken: String): JwtTokens {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            val claims = jwtProvider.getRefreshClaims(refreshToken)
            val login = claims.subject
            val saveRefreshToken = refreshStorage[login]
            if (saveRefreshToken != null && saveRefreshToken == refreshToken) {
                val user: User = userService.getUser(login)
                val accessToken = jwtProvider.generateAccessToken(user)
                return JwtTokens(accessToken, null)
            }
        }
        return JwtTokens(null, null)
    }

    fun refresh(refreshToken: String): JwtTokens {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            val claims = jwtProvider.getRefreshClaims(refreshToken)
            val login = claims.subject
            val saveRefreshToken = refreshStorage[login]
            if (saveRefreshToken != null && saveRefreshToken == refreshToken) {
                val user: User = userService.getUser(login)
                val accessToken = jwtProvider.generateAccessToken(user)
                val newRefreshToken = jwtProvider.generateRefreshToken(user)
                refreshStorage[user.username] = newRefreshToken
                return JwtTokens(accessToken, newRefreshToken)
            }
        }
        throw IllegalArgumentException("Illegal refresh token")
    }

    val authInfo: JwtAuthentication
        get() = SecurityContextHolder.getContext().authentication as JwtAuthentication
}