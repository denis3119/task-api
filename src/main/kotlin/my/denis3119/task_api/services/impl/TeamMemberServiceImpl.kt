package my.denis3119.task_api.services.impl

import my.denis3119.task_api.repositories.TeamMemberRepository
import my.denis3119.task_api.services.TeamMemberService
import org.springframework.stereotype.Service

@Service
class TeamMemberServiceImpl(
    private val teamMemberRepository: TeamMemberRepository
) : TeamMemberService
