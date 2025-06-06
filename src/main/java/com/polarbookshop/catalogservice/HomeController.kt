package com.polarbookshop.catalogservice

import com.polarbookshop.catalogservice.config.PolarProperties
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController(val polarProperties: PolarProperties) {

    @GetMapping("/")
    fun getGreeting(): String {
        return polarProperties.greeting
    }
}