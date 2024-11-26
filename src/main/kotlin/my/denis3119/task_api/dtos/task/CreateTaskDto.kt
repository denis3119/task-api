package my.denis3119.task_api.dtos.task

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import my.denis3119.task_api.enums.TaskPriority
import my.denis3119.task_api.enums.TaskStatus
import java.time.LocalDateTime

@Schema(description = "DTO for creating a new task")
data class CreateTaskDto(
    @field:NotBlank
    @Schema(description = "The title of the task", example = "Complete project report", required = true)
    var title: String,

    @field:NotBlank
    @Schema(
        description = "The description of the task",
        example = "Prepare and submit the final project report",
        required = true
    )
    var description: String,

    @Schema(description = "The due date of the task", example = "2024-12-31T23:59:59")
    var dueDate: LocalDateTime? = null,

    @Schema(description = "The priority of the task", example = "MEDIUM", required = true)
    var priority: TaskPriority,

    @Schema(description = "The status of the task", example = "NEW", required = true)
    var status: TaskStatus,

    @Schema(description = "The ID of the user assigned to the task", example = "1")
    var assignedToId: Long? = null,
)
