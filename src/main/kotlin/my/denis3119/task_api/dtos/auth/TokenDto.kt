package my.denis3119.task_api.dtos.auth

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "DTO for authentication token")
data class TokenDto(
    @Schema(
        description = "The authentication token",
        example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
        required = true
    )
    val token: String
)
