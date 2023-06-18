package org.example.template.application.service

import org.example.template.domain.model.User
import org.example.template.domain.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    val userRepository: UserRepository,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username) ?: throw UsernameNotFoundException(username)
        return org.springframework.security.core.userdetails.User(user.username, user.password, emptyList())
    }

    fun getUser(username: String): User {
        return userRepository.findByUsername(username) ?: throw UsernameNotFoundException(username)
    }

    fun saveUser(user: User) {
        userRepository.saveUser(user)
    }

    fun updateUser(userId: Long, user: User) {
        userRepository.updateUser(userId, user)
    }
}
