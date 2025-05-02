package com.polarbookshop.catalogservice.domain

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import java.time.Instant

data class Book(
    @Id
    val id: Long? = null,

    // The 'field:' prefix is needed for @Valid to pick up
    // but it seems Spring Data annotations do not need it
    // regardless kotlin("plugin.spring") in build.gradle.kts
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

    // Spring Data annotations doe not need the 'field:' prefix
    @Version
    val version: Long? = null,

    @CreatedDate
    // @Column("created_at") // is not needed, the name is inferred from the property name
    val createdAt: Instant? = null,

    @LastModifiedDate
    // @Column("last_modified_at") // originally it's named lastModifiedDate
    val lastModifiedAt: Instant? = null,
)
