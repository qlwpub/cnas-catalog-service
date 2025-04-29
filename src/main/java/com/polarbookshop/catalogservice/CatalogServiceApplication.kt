package com.polarbookshop.catalogservice

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan

@SpringBootApplication
@ConfigurationPropertiesScan
class CatalogServiceApplication

fun main(args: Array<String>) {
    SpringApplication.run(CatalogServiceApplication::class.java, *args)
}
