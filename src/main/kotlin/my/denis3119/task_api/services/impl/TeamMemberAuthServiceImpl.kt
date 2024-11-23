package my.denis3119.task_api.services.impl

import my.denis3119.task_api.dtos.auth.LoginDto
import my.denis3119.task_api.dtos.auth.RegisterDto
import my.denis3119.task_api.dtos.auth.TokenDto
import my.denis3119.task_api.enums.UserRole.USER
import my.denis3119.task_api.services.TeamMemberAuthService
import org.springframework.stereotype.Service

@Service
class TeamMemberAuthServiceImpl(
    private val userDetailsServiceImpl: UserDetailsServiceImpl
) : TeamMemberAuthService {
    override fun register(registerDto: RegisterDto) {
        userDetailsServiceImpl.registerUser(registerDto, USER)
    }

    override fun login(loginDto: LoginDto): TokenDto {
        val token = userDetailsServiceImpl.login(loginDto)
        return TokenDto(token)
    }

}
