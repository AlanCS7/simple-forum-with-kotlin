package dev.alancss.forum.factory

import dev.alancss.forum.dto.TopicByCategoryResponse

object TopicByCategoryFactory {

    fun build(
        category: String = "Programming",
        quantity: Long = 1L
    ) = TopicByCategoryResponse(category, quantity)

}