package edu.telegram.selfsignechotelegrambot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SelfSignEchoTelegramBotApplication

fun main(args: Array<String>) {
	runApplication<SelfSignEchoTelegramBotApplication>(*args)
}
