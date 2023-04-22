package edu.telegram.selfsignechotelegrambot

import org.apache.logging.log4j.LogManager.getLogger
import org.slf4j.LoggerFactory
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.starter.SpringWebhookBot
import java.io.Serializable

class TelegramBot(
    val messageHandler: MessageHandler,
    webhook: SetWebhook
) : SpringWebhookBot(webhook) {
    @JvmField
    var botToken: String? = null
    @JvmField
    var botName: String? = null
    @JvmField
    var botPath: String? = null
    override fun getBotToken() = botToken!!
    override fun getBotUsername() = botName!!
    override fun getBotPath() = botPath!!
    override fun onWebhookUpdateReceived(update: Update): BotApiMethod<*> {
        val chatId = update.message.chatId.toString()
        val answer:BotApiMethod<*> = try {
            return messageHandler.answerMessage(update.message)
        } catch (e: IllegalArgumentException) {
            LOG.error(e.message, e)
            SendMessage(chatId, "Only TEXT!!!")
        } catch (e: Exception) {
            LOG.error(e.message, e)
            SendMessage(chatId, "Something WRONG!")
        }
        return answer
    }

    companion object{
        private val LOG = getLogger(this::class.java)
    }
}