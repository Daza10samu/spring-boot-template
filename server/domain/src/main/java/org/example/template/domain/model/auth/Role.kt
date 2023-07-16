package org.example.template.domain.model.auth

import org.springframework.security.core.GrantedAuthority

enum class Role(
    private val value: String,
) : GrantedAuthority {
    ADMIN("ADMIN"),
    USER("USER");

    override fun getAuthority() = value
}