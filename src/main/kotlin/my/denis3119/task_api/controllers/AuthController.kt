package my.denis3119.task_api.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import my.denis3119.task_api.dtos.auth.LoginDto
import my.denis3119.task_api.dtos.auth.RegisterDto
import my.denis3119.task_api.dtos.auth.TokenDto
import my.denis3119.task_api.services.TeamMemberAuthService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody


@RestController
@RequestMapping("/auth")
@Validated
class AuthController(
    private val teamMemberAuthService: TeamMemberAuthService

) {

    @Operation(
        summary = "Register a new user",
        description = "Allows registration of a new user by providing a username and password.",
        requestBody = SwaggerRequestBody(
            description = "Registration details",
            required = true,
            content = [Content(schema = Schema(implementation = RegisterDto::class))]
        ),
        responses = [
            ApiResponse(responseCode = "201", description = "User registered successfully"),
            ApiResponse(responseCode = "400", description = "Invalid input")
        ]
    )
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerUser(
        @RequestBody registerDto: RegisterDto
    ) {
        teamMemberAuthService.register(registerDto)
    }

    @Operation(
        summary = "Log in an existing user",
        description = "Authenticates a user with their username and password, returning a token upon success.",
        requestBody = SwaggerRequestBody(
            description = "Login details",
            required = true,
            content = [Content(schema = Schema(implementation = LoginDto::class))]
        ),
        responses = [
            ApiResponse(
                responseCode = "200", description = "Login successful, token returned", content = [
                    Content(schema = Schema(implementation = TokenDto::class))
                ]
            ),
            ApiResponse(responseCode = "401", description = "Invalid credentials")
        ]
    )

    @PostMapping("/login")
    fun login(
        @RequestBody loginDto: LoginDto
    ): TokenDto {
        return teamMemberAuthService.login(loginDto)
    }
}
