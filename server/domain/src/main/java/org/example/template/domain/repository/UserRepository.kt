package org.example.template.domain.repository

import org.example.template.domain.model.User

interface UserRepository {
    fun get(id: Long, forUpdate: Boolean = false): User?

    fun findByUsername(username: String): User?
    fun saveUser(user: User)
    fun updateUser(id: Long, user: User)
}
