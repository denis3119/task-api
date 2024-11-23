package my.denis3119.task_api.dtos.task

import jakarta.validation.constraints.NotBlank
import my.denis3119.task_api.dtos.TeamMemberDto
import my.denis3119.task_api.enums.TaskPriority
import my.denis3119.task_api.enums.TaskStatus
import java.time.LocalDateTime

data class CreateTaskDto(
    @NotBlank
    var title: String,
    @NotBlank
    var description: String,
    var startDate: LocalDateTime? = null,
    var endDate: LocalDateTime? = null,
    var dueDate: LocalDateTime? = null,
    var priority: TaskPriority,
    var status: TaskStatus,
    var assignedTo: TeamMemberDto? = null,
)
