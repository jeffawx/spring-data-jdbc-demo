package com.airwallex.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringDataJdbcDemoApplication

fun main(args: Array<String>) {
	runApplication<SpringDataJdbcDemoApplication>(*args)
}
