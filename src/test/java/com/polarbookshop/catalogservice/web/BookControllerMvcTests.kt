package com.polarbookshop.catalogservice.web

import com.polarbookshop.catalogservice.config.SecurityConfig
import com.polarbookshop.catalogservice.domain.BookNotFoundException
import com.polarbookshop.catalogservice.domain.BookService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(BookController::class)
@Import(SecurityConfig::class)
class BookControllerMvcTests {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var bookService: BookService

    @MockitoBean
    @Suppress("unused")
    private lateinit var jwtDecoder: JwtDecoder

    @Test
    fun `user with employee role can delete book with 204 as reply`() {
        val isbn = "1234567890"
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .delete("/books/$isbn")
                    .with(
                        SecurityMockMvcRequestPostProcessors
                            .jwt()
                            .authorities(SimpleGrantedAuthority("ROLE_employee"))
                    )
            )
            .andExpect(status().isNoContent)
    }

    @Test
    fun `user with customer role only can not delete book, with 403 as reply`() {
        val isbn = "1234567890"
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .delete("/books/$isbn")
                    .with(
                        SecurityMockMvcRequestPostProcessors
                            .jwt()
                            .authorities(SimpleGrantedAuthority("ROLE_customer"))
                    )
            ).andExpect(status().isForbidden)
    }

    @Test
    fun `unauthenticated user cannot delete book with 401 reply`() {
        val isbn = "1234567890"
        mockMvc
            .perform(MockMvcRequestBuilders.delete("/books/$isbn"))
            .andExpect(status().isUnauthorized)
    }

    @Test
    fun `when GET books on non-existing book then return 404`() {
        // Given
        val isbn = "1234567890"
        val url = "/books/$isbn"

        given(bookService.viewBookDetail(isbn)).willThrow(BookNotFoundException(isbn))

        // When & Then
        mockMvc.perform(get(url))
            .andExpect(status().isNotFound)
    }
}