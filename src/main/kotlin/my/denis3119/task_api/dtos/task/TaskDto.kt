package my.denis3119.task_api.dtos.task

import my.denis3119.task_api.dtos.TeamMemberDto
import my.denis3119.task_api.dtos.comment.CommentDto
import my.denis3119.task_api.enums.TaskPriority
import my.denis3119.task_api.enums.TaskStatus
import java.time.LocalDateTime

data class TaskDto(
    var id: Long? = null,
    var title: String,
    var description: String,
    var startDate: LocalDateTime? = null,
    var endDate: LocalDateTime? = null,
    var dueDate: LocalDateTime? = null,
    var priority: TaskPriority,
    var status: TaskStatus,
    var assignedTo: TeamMemberDto? = null,
    var comments: List<CommentDto> = emptyList()
)
