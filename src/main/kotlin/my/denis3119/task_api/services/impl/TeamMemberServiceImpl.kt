package my.denis3119.task_api.services.impl

import jakarta.persistence.EntityNotFoundException
import my.denis3119.task_api.models.TeamMember
import my.denis3119.task_api.repositories.TeamMemberRepository
import my.denis3119.task_api.services.TeamMemberService
import org.springframework.stereotype.Service

@Service
class TeamMemberServiceImpl(
    private val teamMemberRepository: TeamMemberRepository
) : TeamMemberService {

    override fun findById(assignedToId: Long): TeamMember {
        return teamMemberRepository.findById(assignedToId)
            .orElseThrow { EntityNotFoundException("Team member with id $assignedToId not found") }
    }
}
