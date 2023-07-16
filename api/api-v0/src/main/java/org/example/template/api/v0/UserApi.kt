package org.example.template.api.v0

import org.example.template.api.v0.dto.user.JwtTokensDto
import org.example.template.api.v0.dto.user.UserDto
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping(
    value = ["/v0/users"],
    produces = [APPLICATION_JSON_VALUE],
)
@RestController
interface UserApi {
    @PostMapping("/register")
    fun register(@RequestBody userDto: UserDto): ResponseEntity<Int>

    @PostMapping("/auth")
    fun auth(@RequestBody userDto: UserDto): ResponseEntity<JwtTokensDto>

    @PostMapping("/refresh")
    fun refresh(@RequestBody refreshToken: String): ResponseEntity<JwtTokensDto>

    @GetMapping("/me")
    fun me(): ResponseEntity<UserDto>
}