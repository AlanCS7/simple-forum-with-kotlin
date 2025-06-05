package dev.alancss.forum.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.servers.Server

@OpenAPIDefinition(
    info = Info(
        title = "Forum API",
        version = "1.0.0",
        description = "API for the Forum application, providing endpoints for user management, posts, and comments.",
        termsOfService = "https://example.com/terms",
        contact = Contact(
            name = "Alan Silva",
            email = "alancss.contact@gmail.com",
            url = "https://github.com/AlanCS7"
        ),
        license = License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0"
        )
    ),
    servers = [
        Server(
            description = "Local Development Server",
            url = "http://localhost:8080"
        )
    ],
    security = [
        SecurityRequirement(
            name = "bearerAuth"
        )
    ]
)
@SecurityScheme(
    name = "bearerAuth",
    description = "JWT Authorization header using the Bearer scheme.",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    `in` = SecuritySchemeIn.HEADER
)
class OpenApiConfig