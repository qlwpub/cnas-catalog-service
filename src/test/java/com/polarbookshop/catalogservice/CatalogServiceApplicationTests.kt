package com.polarbookshop.catalogservice

import com.polarbookshop.catalogservice.domain.Book
import dasniko.testcontainers.keycloak.KeycloakContainer
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("integration") // enables loading of application-integration.yml
@Testcontainers
class CatalogServiceApplicationTests() {

    // this can't be initialized in the constructor
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun contextLoads() {
    }

    @Test
    fun `when POST books then 201 is returned`() {
        val title = "The Hitchhiker's Guide to the Galaxy"
        val author = "Douglas Adams"
        webTestClient.post()
            .uri("/books")
            .contentType(APPLICATION_JSON)
            .bodyValue(
                """
				{
					"isbn": 1234567899,
					"title": "$title",
					"author": "$author",
					"price": 42.0
				}
				""".trimIndent()
            )
            .exchange()
            .expectStatus().isCreated
            .expectBody<Book>()
            .value { it.title == title }
            .value { it.author == author }
    }

    @Test
    fun `POST to books with valid book would cause book created`() {
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

    companion object {
        @Container
        @JvmStatic
        val keycloakContainer = KeycloakContainer("keycloak/keycloak:latest")
            .withRealmImportFile("test-realm-config.json")

        @DynamicPropertySource
        @JvmStatic
        @Suppress("unused")
        fun dynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri") {
                keycloakContainer.authServerUrl + "realms/PolarBookshop"
            }
        }
    }
}
