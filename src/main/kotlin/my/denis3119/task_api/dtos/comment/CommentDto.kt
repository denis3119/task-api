package my.denis3119.task_api.dtos.comment

import my.denis3119.task_api.dtos.TeamMemberDto
import java.time.LocalDateTime

data class CommentDto(
    val id: Long? = null,
    var text: String,
    var taskId: Long,
    var author: TeamMemberDto,
    var createdAt: LocalDateTime? = null
)
