package com.polarbookshop.catalogservice.web

import com.polarbookshop.catalogservice.domain.Book
import com.polarbookshop.catalogservice.domain.BookService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.logging.Logger

@RestController
@RequestMapping("books")
class BookController(val bookService: BookService) {
    @GetMapping
    @Suppress("unused")
    fun get(): Iterable<Book> {
        logger.info("Fetching the list of books in the catalog")
        return bookService.viewBookList()
    }

    @GetMapping("{isbn}")
    @Suppress("unused")
    fun getByIsbn(@PathVariable isbn: String): Book {
        return bookService.viewBookDetail(isbn)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Suppress("unused")
    fun post(@Valid @RequestBody book: Book): Book {
        logger.info{"Creating new book: $book"}
        return bookService.addBook(book)
    }

    @PutMapping("{isbn}")
    @Suppress("unused")
    fun put(
        @PathVariable isbn: String,
        @Valid @RequestBody book: Book,
    ): Book {
        logger.info{"Updating existing book: $book"}
        return bookService.updateBook(isbn, book)
    }

    @DeleteMapping("{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Suppress("unused")
    fun delete(
        @PathVariable isbn: String,
    ) {
        logger.info{"Deleting book with isbn: $isbn"}
        val book = bookService.viewBookDetail(isbn)
        bookService.removeBook(book)
    }

    companion object {
        // just use java.util.logging.Logger, not LoggerFactory,
        // otherwise the info{...} won't work - it seems slf4j API does not support this
        private val logger = Logger.getLogger(BookController::class.java.name)
    }
}