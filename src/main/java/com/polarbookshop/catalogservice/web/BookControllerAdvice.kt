package com.polarbookshop.catalogservice.web

import com.polarbookshop.catalogservice.domain.BookAlreadyExistsException
import com.polarbookshop.catalogservice.domain.BookNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class BookControllerAdvice {
    @ExceptionHandler(BookNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun bookNotFoundHandler(ex: BookNotFoundException): String {
        return ex.message ?: "Book not found"
    }

    @ExceptionHandler(BookAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun bookAlreadyExistsHandler(ex: BookAlreadyExistsException): String {
        return ex.message ?: "Book already exists"
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun methodArgumentNotValidHandler(ex: MethodArgumentNotValidException): Map<String, String> {
        val errors = mutableMapOf<String, String>()
        ex.bindingResult.fieldErrors.forEach { fieldError ->
            errors[fieldError.field] = fieldError.defaultMessage ?: "Invalid value"
        }
        return errors
    }
}