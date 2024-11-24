package my.denis3119.task_api.configs

import my.denis3119.task_api.models.TeamMember
import my.denis3119.task_api.repositories.TeamMemberRepository
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.util.*
import java.util.Optional.ofNullable


@Component
class TeamMemberAuditorAware(
    private val teamMemberRepository: TeamMemberRepository
) : AuditorAware<TeamMember> {

    override fun getCurrentAuditor(): Optional<TeamMember> {
        val authentication = SecurityContextHolder.getContext()?.authentication
        if (authentication == null || !authentication.isAuthenticated || authentication.principal == "anonymousUser") {
            return Optional.empty()
        }

        return ofNullable(authentication.principal)
            .map { teamMemberRepository.findByName(getUserName(it)).orElse(null) }
    }

    private fun getUserName(principal: Any?): String = when (principal) {
        is User -> principal.username
        is String -> principal
        else -> throw IllegalStateException("Invalid principal type")
    }
}
