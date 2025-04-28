package com.polarbookshop.catalogservice.web

import com.polarbookshop.catalogservice.domain.BookNotFoundException
import com.polarbookshop.catalogservice.domain.BookService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(BookController::class)
class BookControllerMvcTests {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var bookService: BookService

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