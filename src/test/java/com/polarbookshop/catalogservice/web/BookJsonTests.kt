package com.polarbookshop.catalogservice.web

import com.polarbookshop.catalogservice.domain.Book
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import java.time.Instant

@JsonTest
class BookJsonTests {
    @Autowired
    private lateinit var json: JacksonTester<Book>

    @Test
    fun `serializes to JSON`() {
        val book = Book(
            isbn = "1234567890",
            title = "Test Book",
            author = "Test Author",
            price = 42.0,
            createdAt = Instant.now(),
            lastModifiedAt = Instant.now(),
        )

        val content = json.write(book)
        assertThat(content).extractingJsonPathStringValue("@.isbn").isEqualTo(book.isbn)
        assertThat(content).extractingJsonPathStringValue("@.title").isEqualTo(book.title)
        assertThat(content).extractingJsonPathStringValue("@.author").isEqualTo(book.author)
        assertThat(content).extractingJsonPathNumberValue("@.price").isEqualTo(book.price)
    }

    @Test
    fun `deserializes from JSON`() {
        val isbn = "1234567890"
        val title = "Test Book"
        val author = "Test Author"
        val price = 42.0

        val content = """
            {
                "isbn": "$isbn",
                "title": "$title",
                "author": "$author",
                "price": $price
            }
        """.trimIndent()

        val book = json.parse(content).getObject()

        val expectedBook = Book(
            isbn = isbn,
            title = title,
            author = author,
            price = price
        )

        assertThat(book).isEqualTo(expectedBook)
    }
}