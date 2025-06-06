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
        val existingBook = bookRepo.findByIsbn(isbn)

        return if (existingBook == null) {
            addBook(book)
        } else {
            val bookToUpdate = Book(
                id = existingBook.id,
                isbn = isbn,
                title = book.title,
                author = book.author,
                price = book.price,
                version = existingBook.version,
                createdAt = existingBook.createdAt,
                createdBy = existingBook.createdBy,
                lastModifiedAt = existingBook.lastModifiedAt,
                lastModifiedBy = existingBook.lastModifiedBy,
                )
            bookRepo.save(bookToUpdate)
        }
    }
}