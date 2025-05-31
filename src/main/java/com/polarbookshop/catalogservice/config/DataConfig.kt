package com.polarbookshop.catalogservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing
import org.springframework.security.core.context.SecurityContextHolder

@Configuration
@EnableJdbcAuditing
class DataConfig{

    @Bean
    fun auditorAware() : AuditorAware<String> {
        return AuditorAware<String> {
            SecurityContextHolder.getContext()?.authentication.takeIf { it.isAuthenticated }?.name }
        }
    }
}

