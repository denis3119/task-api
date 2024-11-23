package my.denis3119.task_api.dtos.auth

import jakarta.validation.constraints.NotBlank

data class RegisterDto(
    @NotBlank
    val username: String,
    @NotBlank
    val password: String
)
