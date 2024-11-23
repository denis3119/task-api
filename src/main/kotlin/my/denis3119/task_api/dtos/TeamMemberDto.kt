package my.denis3119.task_api.dtos

import my.denis3119.task_api.enums.UserRole

data class TeamMemberDto(
    val id: Long?,
    val name: String,
    val password: String,
    val role: UserRole
)
