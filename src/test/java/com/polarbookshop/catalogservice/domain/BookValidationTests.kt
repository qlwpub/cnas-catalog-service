package com.polarbookshop.catalogservice.domain

import jakarta.validation.Validation
import jakarta.validation.Validator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BookValidationTests() {
    @Test
    fun `book with blank title should be invalid`() {
        val book = Book(
            isbn = "1234567890",
            title = "",
            author = "Author",
            price = 10.0
        )
        val violations = validator.validate(book)
        assertThat(violations).hasSize(1)
        assertThat(violations.first().message).isEqualTo("Title must be defined")
    }

    @Test
    fun `book with blank author should be invalid`() {
        val book = Book(
            isbn = "1234567890",
            title = "Title",
            author = "",
            price = 10.0
        )
        val violations = validator.validate(book)
        assertThat(violations).hasSize(1)
        assertThat(violations.first().message).isEqualTo("Author must be defined")
    }

    @Test
    fun `book with blank isbn should be invalid`() {
        val book = Book(
            isbn = "",
            title = "Title",
            author = "Author",
            price = 10.0
        )
        val violations = validator.validate(book)
        assertThat(violations).isNotEmpty
        val notBlankViolation = violations.firstOrNull { it.message == "ISBN must not be blank" }
        assertThat(notBlankViolation).isNotNull
    }

    @Test
    fun `book with invalid isbn should be invalid`() {
        val book = Book(
            isbn = "123456789",
            title = "Title",
            author = "Author",
            price = 10.0
        )
        val violations = validator.validate(book)
        assertThat(violations).hasSize(1)
        assertThat(violations.first().message).isEqualTo("ISBN must be a valid ISBN-10/13 number")
    }

    @Test
    fun `book with negative price should be invalid`() {
        val book = Book(
            isbn = "1234567890",
            title = "Title",
            author = "Author",
            price = -10.0
        )
        val violations = validator.validate(book)
        assertThat(violations).hasSize(1)
        assertThat(violations.first().message).isEqualTo("Price must be greater than zero")
    }

    @Test
    fun `book with zero price should be invalid`() {
        val book = Book(
            isbn = "1234567890",
            title = "Title",
            author = "Author",
            price = 0.0
        )
        val violations = validator.validate(book)
        assertThat(violations).hasSize(1)
        assertThat(violations.first().message).isEqualTo("Price must be greater than zero")
    }

    @Test
    fun `book with valid data should be valid`() {
        val book = Book(
            isbn = "1234567890",
            title = "Title",
            author = "Author",
            price = 10.0
        )
        val violations = validator.validate(book)
        assertThat(violations).isEmpty()
    }

    companion object {
        val validator: Validator = Validation.buildDefaultValidatorFactory().validator
    }
}