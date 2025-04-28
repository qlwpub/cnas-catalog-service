package com.polarbookshop.catalogservice.domain

class BookNotFoundException(isbn: String) : RuntimeException("Book not found: $isbn") {

}
