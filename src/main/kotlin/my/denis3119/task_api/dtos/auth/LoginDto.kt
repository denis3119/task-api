package my.denis3119.task_api.dtos.auth

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "Data Transfer Object for user login")
data class LoginDto(
    @field:NotBlank
    @Schema(description = "The username of the user", example = "UserTestName")
    val username: String,

    @field:NotBlank
    @Schema(description = "The password of the user", example = "securePassword123")
    val password: String
)
