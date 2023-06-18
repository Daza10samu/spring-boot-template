package org.example.template.domain.model

data class User(
    val id: Long?,
    val username: String,
    val password: String,
) {
    override fun toString(): String {
        return "UserDto(id=$id, username=$username, password=\"HIDDEN\")"
    }
}
