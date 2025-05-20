package com.polarbookshop.catalogservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
@Configuration
@Suppress("unused")
class SecurityConfig {

    @Bean
    @Suppress("unused")
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .authorizeHttpRequests {
                it
                    .requestMatchers(HttpMethod.GET, "/", "/books/**").permitAll()
                    .anyRequest().hasRole("employee")
            }
            .oauth2ResourceServer { it.jwt { } }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .csrf { it.disable() }
            .build()
    }

    @Bean
    @Suppress("unused")
    fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val jwtGAC = JwtGrantedAuthoritiesConverter().apply {
            setAuthorityPrefix("ROLE_")
            setAuthoritiesClaimName("roles")
        }

        return JwtAuthenticationConverter().apply { setJwtGrantedAuthoritiesConverter(jwtGAC) }
    }
}
