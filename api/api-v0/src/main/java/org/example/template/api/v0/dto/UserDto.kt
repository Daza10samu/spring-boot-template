package org.example.template.api.v0.dto

data class UserDto(
    val id: Long? = null,
    val username: String,
    val password: String
) {
    override fun toString(): String {
        return "UserDto(id=$id, username=$username, password=\"HIDDEN\")"
    }
}