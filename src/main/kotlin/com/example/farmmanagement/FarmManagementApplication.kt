package com.example.farmmanagement

import org.n52.jackson.datatype.jts.JtsModule
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class FarmManagementApplication {
    @Bean
    fun jtsModule(): JtsModule? {
        return JtsModule()
    }
}

fun main(args: Array<String>) {
    runApplication<FarmManagementApplication>(*args)
}
