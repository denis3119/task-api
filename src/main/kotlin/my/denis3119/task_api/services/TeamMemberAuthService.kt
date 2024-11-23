package my.denis3119.task_api.services

import my.denis3119.task_api.dtos.auth.LoginDto
import my.denis3119.task_api.dtos.auth.RegisterDto
import my.denis3119.task_api.dtos.auth.TokenDto

interface TeamMemberAuthService {
    fun register(registerDto: RegisterDto)
    fun login(loginDto: LoginDto): TokenDto
}
