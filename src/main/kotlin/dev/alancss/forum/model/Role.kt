package dev.alancss.forum.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.security.core.GrantedAuthority

@Entity
class Role(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(name = "name", nullable = false)
    val name: String
) : GrantedAuthority {
    override fun getAuthority(): String = name
}