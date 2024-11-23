package my.denis3119.task_api.mapper

import my.denis3119.task_api.dtos.TeamMemberDto
import my.denis3119.task_api.models.TeamMember

object TeamMemberMapper {

    fun TeamMember.toDto() = TeamMemberDto(
        id = id,
        name = name,
        password = password,
        role = role
    )

    fun TeamMemberDto.toEntity() = TeamMember(
        id = id,
        name = name,
        password = password,
        role = role
    )
}
