package com.polarbookshop.catalogservice.domain

import com.polarbookshop.catalogservice.config.DataConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE
import org.springframework.context.annotation.Import
import org.springframework.data.jdbc.core.JdbcAggregateTemplate
import org.springframework.test.context.ActiveProfiles

/**
 * Data slice test
 */
@DataJdbcTest
@Import(DataConfig::class)  // needed to enable auditing!
@AutoConfigureTestDatabase(replace = NONE) // disable the default in-memory database
@ActiveProfiles("integration") // enables loading of application-integration.yml
class BookRepositoryJdbcTests {
    @Autowired
    lateinit var bookRepository: BookRepository

    @Autowired
    lateinit var jdbcAggregateTemplate : JdbcAggregateTemplate

    @Test
    fun `book can be found by isbn`() {
        val isbn = "3245234890"
        val book = Book(isbn = isbn, title = "Test Book", author = "Test Author", price = 9.99)
        jdbcAggregateTemplate.insert(book)

        val found = bookRepository.findByIsbn(isbn)
        assertThat(found).isNotNull()
        assertThat(found?.isbn).isEqualTo(isbn)
    }
}