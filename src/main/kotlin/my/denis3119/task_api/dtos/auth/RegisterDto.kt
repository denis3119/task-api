package my.denis3119.task_api.dtos.auth

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "DTO for user registration")
data class RegisterDto(
    @field:NotBlank
    @Schema(description = "The username of the user", example = "UserTestName", required = true)
    val username: String,

    @field:NotBlank
    @Schema(description = "The password of the user", example = "securePassword123", required = true)
    val password: String
)
