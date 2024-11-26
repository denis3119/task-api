package my.denis3119.task_api.dtos

import io.swagger.v3.oas.annotations.media.Schema
import my.denis3119.task_api.enums.UserRole

@Schema(description = "DTO representing a team member")
data class TeamMemberDto(
    @Schema(description = "The unique identifier of the team member", example = "1")
    val id: Long?,

    @Schema(description = "The name of the team member", example = "UserTestName", required = true)
    val name: String,

    @Schema(description = "The password of the team member", example = "securePassword123", required = true)
    val password: String,

    @Schema(description = "The role of the team member", example = "USER", required = true)
    val role: UserRole
)
