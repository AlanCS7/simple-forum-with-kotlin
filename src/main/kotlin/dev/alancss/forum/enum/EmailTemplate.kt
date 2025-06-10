package dev.alancss.forum.enum

enum class EmailTemplate(val from: String = "no-reply@forumapp.com", val subject: String, val text: String) {
    TOPIC_ANSWERED(
        subject = "[Forum] Your topic has a new reply",
        text = """
            Hello %s,

            Your topic has been answered!
            Visit the forum to check out the new reply.

            Best regards,
            Forum Team
        """.trimIndent()
    ),

    PASSWORD_RESET(
        subject = "[Forum] Password reset request",
        text = """
            We received a request to reset your password.
            Click the link below to continue.

            Best regards,
            Forum Team
        """.trimIndent()
    )
}