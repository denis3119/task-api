package my.denis3119.task_api.services.impl

import my.denis3119.task_api.dtos.RegisterDto
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

    override fun login(username: String, password: String) {
        val token = userDetailsServiceImpl.login(username, password)

    }

}
