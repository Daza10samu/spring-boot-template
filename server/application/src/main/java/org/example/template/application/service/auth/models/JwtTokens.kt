package org.example.template.application.service.auth.models

data class JwtTokens(
    val accessToken: String?,
    val refreshToken: String?,
)