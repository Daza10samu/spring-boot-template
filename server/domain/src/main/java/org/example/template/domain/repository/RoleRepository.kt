package org.example.template.domain.repository

import org.example.template.domain.model.auth.Role

interface RoleRepository {
    fun getRoles(userId: Long): Set<Role>

    fun addRoles(userId: Long, roles: Set<Role>): Int

    fun removeRoles(userId: Long, roles: Set<Role>): Int
}