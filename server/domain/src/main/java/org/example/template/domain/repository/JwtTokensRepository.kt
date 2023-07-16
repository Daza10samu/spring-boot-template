package org.example.template.domain.repository

import org.example.template.domain.model.auth.JwtToken

interface JwtTokensRepository {
    fun saveToken(token: JwtToken)

    fun getToken(tokenString: String): JwtToken?

    fun getRevokedTokens(): Collection<JwtToken>

    fun revokeToken(tokenString: String)

    fun revokeTokensByUserId(userId: Long)

    fun deleteExpiredTokens()
}
