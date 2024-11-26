package my.denis3119.task_api.dtos.task.report

import java.time.LocalDate

data class GeneralTaskReportDto(
    var from: LocalDate,
    var to: LocalDate,
    var completed: Long,
    var averageCompletionTimeInMinutes: Long
)
