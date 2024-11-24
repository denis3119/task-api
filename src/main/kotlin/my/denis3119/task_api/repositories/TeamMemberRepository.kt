package my.denis3119.task_api.repositories

import my.denis3119.task_api.models.TeamMember
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TeamMemberRepository : JpaRepository<TeamMember, Long> {
    fun findByName(username: String): Optional<TeamMember>
}
