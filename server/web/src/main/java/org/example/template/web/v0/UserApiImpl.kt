package org.example.template.web.v0

import org.example.template.api.v0.UserApi
import org.example.template.api.v0.dto.user.JwtTokensDto
import org.example.template.api.v0.dto.user.UserDto
import org.example.template.application.service.auth.AuthService
import org.example.template.application.service.auth.UserDetailsServiceImpl
import org.example.template.application.service.auth.models.JwtTokens
import org.example.template.domain.model.auth.User
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller

@Controller
class UserApiImpl(
    private val userDetailsServiceImpl: UserDetailsServiceImpl,
    private val authService: AuthService,
) : UserApi {
    override fun register(userDto: UserDto): ResponseEntity<Int> {
        LOG.info("Register $userDto")
        try {
            userDetailsServiceImpl.saveUser(userDto.toModel())
        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.CONFLICT)
        }
        return ResponseEntity(HttpStatus.ACCEPTED)
    }

    override fun auth(userDto: UserDto): ResponseEntity<JwtTokensDto> {
        LOG.info("Login $userDto")
        val jwt = authService.login(userDto.username, userDto.password)

        return ResponseEntity(jwt.toDto(), HttpStatus.OK)
    }

    override fun refresh(refreshToken: String): ResponseEntity<JwtTokensDto> {
        val jwt = authService.refresh(refreshToken)

        return ResponseEntity(jwt.toDto(), HttpStatus.OK)
    }

    override fun me(): ResponseEntity<UserDto> {
        val username = SecurityContextHolder.getContext().authentication.name
        LOG.info("Username $username")
        return ResponseEntity(userDetailsServiceImpl.getUser(username).toDto(), HttpStatus.OK)
    }

    private fun UserDto.toModel(): User = userDetailsServiceImpl.createUserModel(id, username, password)

    companion object {
        private val LOG = LoggerFactory.getLogger(UserApiImpl::class.java)

        private fun User.toDto(): UserDto = UserDto(
            id, username, password
        )

        private fun JwtTokens.toDto(): JwtTokensDto = JwtTokensDto(accessToken, refreshToken)
    }
}


