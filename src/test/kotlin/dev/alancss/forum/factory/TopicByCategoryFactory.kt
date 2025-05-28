package dev.alancss.forum.factory

import dev.alancss.forum.dto.TopicByCategoryDto

object TopicByCategoryFactory {

    fun build(
        category: String = "Programming",
        quantity: Long = 1L
    ) = TopicByCategoryDto(category, quantity)

}