package org.example.template.web.v0

import org.example.template.api.v0.UserApi
import org.example.template.api.v0.dto.UserDto
import org.example.template.application.service.UserDetailsServiceImpl
import org.example.template.domain.model.User
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller

@Controller
class UserApiImpl(
    val passwordEncoder: PasswordEncoder,
    val userDetailsServiceImpl: UserDetailsServiceImpl,
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

    override fun auth(userDto: UserDto) {
        LOG.info("Login $userDto")
        val username = SecurityContextHolder.getContext().authentication.name
        LOG.info("Username $username")
    }

    private fun UserDto.toModel(): User = User(id, username, passwordEncoder.encode(password))

    companion object {
        private val LOG = LoggerFactory.getLogger(UserApiImpl::class.java)
    }
}