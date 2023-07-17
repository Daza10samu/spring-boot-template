package org.example.template.domain.repository

import org.example.template.domain.model.auth.Role
import org.example.template.domain.model.auth.User

interface UserRepository {
    fun get(id: Long, forUpdate: Boolean = false): User?
    fun findByUsername(username: String): User?
    fun saveUser(user: User)
    fun updateUser(id: Long, user: User)
    fun getAllUsers(): List<User>
    fun disableUser(id: Long)
    fun addRole(userId: Long, role: Role)
    fun removeRole(userId: Long, role: Role)
}
