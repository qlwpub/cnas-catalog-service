package com.polarbookshop.catalogservice;

import com.polarbookshop.catalogservice.domain.Book
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.expectBody

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class CatalogServiceApplicationTests() {

	// this can't be initialized in the constructor
	@Autowired
	lateinit var webTestClient: WebTestClient

	@Test
	fun contextLoads() {
	}

/*
	@Test
	fun `when POST books then 201 is returned`() {
		webTestClient.post()
			.uri("/books")
			.bodyValue(
				"""
				{
					"title": "The Hitchhiker's Guide to the Galaxy",
					"author": "Douglas Adams",
					"price": 42.0,
					"stock": 3
				}
				""".trimIndent()
			)
			.exchange()
			.expectStatus().isCreated
			.expectBody<Book>()
			.value { it.title == "The Hitchhiker's Guide to the Galaxy" }
	}
*/

	@Test
	fun `POST to books with valid book would cause book created`(){
		val book = Book(
            isbn = "1234567890",
            title = "The Hitchhiker's Guide to the Galaxy",
            author = "Douglas Adams",
            price = 42.0
        )

		webTestClient
			.post()
			.uri("/books")
			.bodyValue(book)
			.exchange()
			.expectStatus().isCreated
			.expectBody<Book>()
			.equals(book)
	}
}
