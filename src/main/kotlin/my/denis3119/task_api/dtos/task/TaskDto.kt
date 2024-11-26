package my.denis3119.task_api.dtos.task

import io.swagger.v3.oas.annotations.media.Schema
import my.denis3119.task_api.dtos.TeamMemberDto
import my.denis3119.task_api.dtos.comment.CommentDto
import my.denis3119.task_api.enums.TaskPriority
import my.denis3119.task_api.enums.TaskStatus
import java.time.LocalDateTime

@Schema(description = "DTO representing a task")
data class TaskDto(
    @Schema(description = "The unique identifier of the task", example = "1")
    var id: Long? = null,

    @Schema(description = "The title of the task", example = "Complete project report", required = true)
    var title: String,

    @Schema(
        description = "The description of the task",
        example = "Prepare and submit the final project report",
        required = true
    )
    var description: String,

    @Schema(description = "The start date of the task", example = "2024-11-01T09:00:00")
    var startDate: LocalDateTime? = null,

    @Schema(description = "The end date of the task", example = "2024-11-05T17:00:00")
    var endDate: LocalDateTime? = null,

    @Schema(description = "The due date of the task", example = "2024-12-31T23:59:59")
    var dueDate: LocalDateTime? = null,

    @Schema(description = "The priority of the task", example = "HIGH", required = true)
    var priority: TaskPriority,

    @Schema(description = "The status of the task", example = "IN_PROGRESS", required = true)
    var status: TaskStatus,

    @Schema(description = "The team member assigned to the task")
    var assignedTo: TeamMemberDto? = null,

    @Schema(description = "The list of comments associated with the task")
    var comments: List<CommentDto> = emptyList()
)
