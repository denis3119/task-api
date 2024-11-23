package my.denis3119.task_api.dtos.auth

import jakarta.validation.constraints.NotBlank

data class LoginDto(
    @NotBlank
    val username: String,
    @NotBlank
    val password: String
)
