package com.polarbookshop.catalogservice.domain

class BookAlreadyExistsException(isbn: String) : RuntimeException("Book already exists: $isbn") {

}
