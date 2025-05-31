package com.polarbookshop.catalogservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@Configuration
@EnableJdbcAuditing
class DataConfig {

    @Bean
    @Suppress("unused")
    fun auditorAware(): AuditorAware<String> {
        return AuditorAware {
            Optional.ofNullable(SecurityContextHolder.getContext())
                .map { it.authentication }
                .filter { it.isAuthenticated }
                .map { it.name }
        }
    }
}

