package my.denis3119.task_api.services.impl

import jakarta.persistence.EntityNotFoundException
import my.denis3119.task_api.configs.JwtUtil
import my.denis3119.task_api.dtos.auth.LoginDto
import my.denis3119.task_api.dtos.auth.RegisterDto
import my.denis3119.task_api.enums.UserRole
import my.denis3119.task_api.models.TeamMember
import my.denis3119.task_api.repositories.TeamMemberRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val teamMemberRepository: TeamMemberRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = teamMemberRepository.findByName(username).orElseThrow { EntityNotFoundException("User not found") }

        return User(
            user.name,
            user.password,
            listOf(SimpleGrantedAuthority("ROLE_${user.role}"))
        )
    }

    fun registerUser(registerDto: RegisterDto, role: UserRole): TeamMember {
        val encodedPassword = passwordEncoder.encode(registerDto.password)
        val newUser = TeamMember(name = registerDto.username, password = encodedPassword, role = role)
        return teamMemberRepository.save(newUser)
    }

    fun login(loginDto: LoginDto): String {
        val userDetails = loadUserByUsername(loginDto.username)
        if (!BCryptPasswordEncoder().matches(loginDto.password, userDetails.password)) {
            throw IllegalArgumentException("Invalid credentials")
        }

        val token = JwtUtil.generateToken(loginDto.username)
        return token
    }
}
