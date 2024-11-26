package my.denis3119.task_api.services

import my.denis3119.task_api.dtos.task.report.GeneralTaskReportDto
import my.denis3119.task_api.dtos.task.report.PersonalTaskReportDto
import java.time.LocalDate

interface TaskReportService {
    fun generateReport(from: LocalDate, to: LocalDate): GeneralTaskReportDto
    fun generatePersonalReport(from: LocalDate, to: LocalDate, teamMemberId: Long): PersonalTaskReportDto
}
