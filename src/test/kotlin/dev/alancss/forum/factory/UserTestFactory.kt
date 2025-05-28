package dev.alancss.forum.factory

import dev.alancss.forum.model.Role
import dev.alancss.forum.model.User

object UserTestFactory {

    fun build(
        id: Long? = 1L,
        name: String = "User",
        email: String = "user@email.com",
        password: String = "password",
        roles: List<Role> = emptyList()
    ): User {
        return User(
            id = id,
            name = name,
            email = email,
            password = password,
            roles = roles
        )
    }
}