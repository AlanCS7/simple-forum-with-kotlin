package dev.alancss.forum.service

import dev.alancss.forum.enum.EmailTemplate
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val javaMailSender: JavaMailSender
) {

    fun sendEmail(authorEmail: String, authorName: String, template: EmailTemplate) {
        val message = SimpleMailMessage().apply {
            from = template.from
            subject = template.subject
            text = template.text.format(authorName)
            setTo(authorEmail)
        }

        javaMailSender.send(message)
    }
}