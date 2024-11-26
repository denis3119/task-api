package my.denis3119.task_api.dtos.task.report

import java.time.LocalDate

data class PersonalTaskReportDto(
    var from: LocalDate,
    var to: LocalDate,
    var teamMemberId: Long,
    var completed: Long,
    var averageCompletionTimeInMinutes: Long
)
