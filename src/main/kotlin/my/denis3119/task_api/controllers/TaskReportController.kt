package my.denis3119.task_api.controllers

import my.denis3119.task_api.dtos.task.report.GeneralTaskReportDto
import my.denis3119.task_api.dtos.task.report.PersonalTaskReportDto
import my.denis3119.task_api.services.TaskReportService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate


@RestController
@RequestMapping("/reports/tasks")
@Validated
@PreAuthorize("hasRole('ROLE_ADMIN')")
class TaskReportController(
    private val taskReportService: TaskReportService
) {

    @GetMapping("/general/generate")
    fun generateReport(
        @RequestParam("from") from: LocalDate,
        @RequestParam("to") to: LocalDate
    ): GeneralTaskReportDto {
        return taskReportService.generateReport(from, to)
    }

    @GetMapping("/personal/generate")
    fun generatePersonalReport(
        @RequestParam("from") from: LocalDate,
        @RequestParam("to") to: LocalDate,
        @RequestParam("teamMemberId") teamMemberId: Long
    ): PersonalTaskReportDto {
        return taskReportService.generatePersonalReport(from, to, teamMemberId)
    }

}
