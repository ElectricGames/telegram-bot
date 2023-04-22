package edu.telegram.selfsignechotelegrambot

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import org.telegram.telegrambots.updatesreceivers.DefaultWebhook

@Configuration
class TelegramConfig(
    @Value("\${telegram.bot.webhook.path}")
    val webhookPath: String,
    @Value("\${telegram.bot.name}")
    val botName: String,
    @Value("\${telegram.bot.token}")
    val botToken: String
) {

    @Bean
    fun setWebhookInstance(): SetWebhook = SetWebhook.builder().url(webhookPath).build()

    @Bean
    fun getTelegramBot(messageHandler: MessageHandler, setWebhook: SetWebhook): TelegramBot {
        val telegramBot = TelegramBot(messageHandler, setWebhook)
        telegramBot.botPath = "webhook"
        telegramBot.botToken = botToken
        telegramBot.botName = botName
        try {
            val defaultWebhook = DefaultWebhook()
            defaultWebhook.setInternalUrl("http://localhost:8081")
            val telegramBotsApi = TelegramBotsApi(DefaultBotSession::class.java, defaultWebhook)
            telegramBotsApi.registerBot(telegramBot, telegramBot.setWebhook)
        } catch (e: Exception) {
            throw RuntimeException("Can't register telegram bot", e)
        }
        return telegramBot
    }
}