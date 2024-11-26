package my.denis3119.task_api.services.impl

import my.denis3119.task_api.dtos.task.report.GeneralTaskReportDto
import my.denis3119.task_api.dtos.task.report.PersonalTaskReportDto
import my.denis3119.task_api.repositories.TaskRepository
import my.denis3119.task_api.services.TaskReportService
import my.denis3119.task_api.utils.DateUtils.atEndOfDay
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class TaskReportServiceImpl(
    private val taskRepository: TaskRepository
) : TaskReportService {
    override fun generateReport(from: LocalDate, to: LocalDate): GeneralTaskReportDto {
        val averageCompletionTime = taskRepository.getAverageCompletionTimeInMinutes(
            from.atStartOfDay(),
            to.atEndOfDay()
        )

        val generalTaskReportDto = GeneralTaskReportDto(
            from = from,
            to = to,
            completed = averageCompletionTime.totalTaskCount,
            averageCompletionTimeInMinutes = averageCompletionTime.averageTimeInMinutes
        )
        return generalTaskReportDto
    }

    override fun generatePersonalReport(from: LocalDate, to: LocalDate, teamMemberId: Long): PersonalTaskReportDto {
        val averageCompletionTime = taskRepository.getAverageCompletionTimeInMinutes(
            from.atStartOfDay(),
            to.atEndOfDay(),
            teamMemberId
        )

        val generalReportDto = PersonalTaskReportDto(
            from = from,
            to = to,
            teamMemberId = teamMemberId,
            completed = averageCompletionTime.totalTaskCount,
            averageCompletionTimeInMinutes = averageCompletionTime.averageTimeInMinutes
        )
        return generalReportDto
    }

}
