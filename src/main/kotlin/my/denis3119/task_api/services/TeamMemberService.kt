package my.denis3119.task_api.services

import my.denis3119.task_api.models.TeamMember

interface TeamMemberService {
    fun findById(assignedToId: Long): TeamMember
}
