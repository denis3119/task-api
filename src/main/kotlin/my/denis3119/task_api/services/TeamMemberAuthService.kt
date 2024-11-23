package my.denis3119.task_api.services

import my.denis3119.task_api.dtos.RegisterDto

interface TeamMemberAuthService {
    fun register(registerDto: RegisterDto)
    fun login(username: String, password: String)
}
