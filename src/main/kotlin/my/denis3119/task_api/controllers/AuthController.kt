package my.denis3119.task_api.controllers

import my.denis3119.task_api.dtos.RegisterDto
import my.denis3119.task_api.dtos.TokenDto
import my.denis3119.task_api.services.TeamMemberAuthService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
@Validated
class AuthController(
    private val teamMemberAuthService: TeamMemberAuthService

) {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerUser(
        @RequestBody registerDto: RegisterDto
    ) {
        teamMemberAuthService.register(registerDto)
    }

    @PostMapping("/login")
    fun login(
        @RequestParam username: String,
        @RequestParam password: String
    ): TokenDto {
        return teamMemberAuthService.login(username, password)
    }
}
