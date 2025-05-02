package com.polarbookshop.catalogservice.domain

import org.springframework.stereotype.Service

@Service
class BookService(
    val bookRepo: BookRepository,
) {
    fun viewBookList(): Iterable<Book> {
        return bookRepo.findAll()
    }

    fun viewBookDetail(isbn: String): Book {
        return bookRepo.findByIsbn(isbn) ?: throw BookNotFoundException(isbn)
    }

    fun addBook(book: Book): Book {
        if (bookRepo.existsByIsbn(book.isbn)) {
            throw BookAlreadyExistsException(book.isbn)
        }
        return bookRepo.save(book)
    }

    fun removeBook(book: Book) {
        bookRepo.delete(book.isbn)
    }

    fun updateBook(isbn: String, book: Book): Book {
        val found = bookRepo.findByIsbn(isbn)

        return if (found == null) {
            addBook(book)
        } else {
            val bookToUpdate = Book(
                id = found.id,
                isbn = isbn,
                title = book.title,
                author = book.author,
                price = book.price,
                version = found.version,
                createdAt = found.createdAt,
                lastModifiedDate = found.lastModifiedDate,
                )
            bookRepo.save(bookToUpdate)
        }
    }
}