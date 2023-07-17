package org.example.template.application.service.auth

import org.example.template.domain.repository.JwtTokensRepository
import org.springframework.stereotype.Service
import java.util.concurrent.Executors

@Service
class ExpiredTokensCleaner(
    private val jwtTokensRepository: JwtTokensRepository,
) {
    private val executor = Executors.newSingleThreadScheduledExecutor()

    init {
        executor.scheduleAtFixedRate(
            ::clean,
            0,
            1,
            java.util.concurrent.TimeUnit.HOURS,
        )
    }

    private fun clean() {
        try {
            jwtTokensRepository.deleteExpiredTokens()
        } catch (e: Exception) {
            log.error("Error while cleaning expired tokens", e)
        }
    }

    companion object {
        private val log = org.slf4j.LoggerFactory.getLogger(ExpiredTokensCleaner::class.java)
    }
}
