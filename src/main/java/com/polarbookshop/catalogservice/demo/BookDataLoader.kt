package com.polarbookshop.catalogservice.demo

import com.polarbookshop.catalogservice.domain.Book
import com.polarbookshop.catalogservice.domain.BookRepository
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Profile
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
@Profile("testdata")
class BookDataLoader(val bookrepository: BookRepository) {

    @EventListener(ApplicationReadyEvent::class)
    fun loadTestData() {
        val books = listOf(
            Book(
                isbn = "1234567891",
                title = "Test Book 1",
                author = "Test Author 1",
                price = 42.0,
            ),
            Book(
                isbn = "1234567892",
                title = "Test Book 2",
                author = "Test Author 2",
                price = 42.2,
            ),
            Book(
                isbn = "1234567893",
                title = "Test Book 3",
                author = "Test Author 3",
                price = 42.3,
            ),
            Book(
                isbn = "1234567894",
                title = "Test Book 4",
                author = "Test Author 4",
                price = 42.4,
            ),
        )

        for (book in books) {
            if (!bookrepository.existsByIsbn(book.isbn)) {
                bookrepository.save(book)
            }
        }
    }
}