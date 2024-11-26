package my.denis3119.task_api.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import my.denis3119.task_api.dtos.task.report.GeneralTaskReportDto
import my.denis3119.task_api.dtos.task.report.PersonalTaskReportDto
import my.denis3119.task_api.services.TaskReportService
import org.springframework.format.annotation.DateTimeFormat
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

    @Operation(
        summary = "Generate general task report",
        description = "Generates a report for all tasks completed within a specified date range.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Report generated successfully",
                content = [Content(schema = Schema(implementation = GeneralTaskReportDto::class))]
            ),
            ApiResponse(responseCode = "400", description = "Invalid date range provided"),
            ApiResponse(responseCode = "403", description = "Access denied")
        ]
    )
    @GetMapping("/general/generate")
    fun generateReport(
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @RequestParam("from") from: LocalDate,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @RequestParam("to") to: LocalDate
    ): GeneralTaskReportDto {
        return taskReportService.generateReport(from, to)
    }

    @Operation(
        summary = "Generate personal task report",
        description = "Generates a report for tasks completed by a specific team member within a specified date range.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Report generated successfully",
                content = [Content(schema = Schema(implementation = PersonalTaskReportDto::class))]
            ),
            ApiResponse(responseCode = "400", description = "Invalid input parameters"),
            ApiResponse(responseCode = "403", description = "Access denied"),
            ApiResponse(responseCode = "404", description = "Team member not found")
        ]
    )
    @GetMapping("/personal/generate")
    fun generatePersonalReport(
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @RequestParam("from") from: LocalDate,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @RequestParam("to") to: LocalDate,

        @RequestParam("teamMemberId") teamMemberId: Long
    ): PersonalTaskReportDto {
        return taskReportService.generatePersonalReport(from, to, teamMemberId)
    }

}
