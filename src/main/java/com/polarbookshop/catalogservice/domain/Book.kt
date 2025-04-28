package com.polarbookshop.catalogservice.domain

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive

data class Book(
    @field:NotBlank(message = "ISBN must not be blank")
    @field:Pattern(
        regexp = "^[0-9]{10}|[0-9]{13}$",
        message = "ISBN must be a valid ISBN-10/13 number"
    )
    val isbn: String,

    @field:NotBlank(message = "Title must be defined")
    val title: String,

    @field:NotBlank(message = "Author must be defined")
    val author: String,

    @field:NotNull(message = "Price must be defined")
    @field:Positive(message = "Price must be greater than zero")
    val price: Double,
)
