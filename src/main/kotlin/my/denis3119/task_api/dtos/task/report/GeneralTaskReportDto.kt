package my.denis3119.task_api.dtos.task.report

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(description = "General task report")
data class GeneralTaskReportDto(

    @Schema(description = "Start date of the report period", example = "2024-01-01")
    var from: LocalDate,

    @Schema(description = "End date of the report period", example = "2024-01-31")
    var to: LocalDate,

    @Schema(description = "Number of completed tasks", example = "15")
    var completed: Long,

    @Schema(description = "Average task completion time in minutes", example = "120")
    var averageCompletionTimeInMinutes: Long
)
