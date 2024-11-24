package my.denis3119.task_api.services.impl

import jakarta.persistence.EntityNotFoundException
import my.denis3119.task_api.dtos.task.CreateTaskDto
import my.denis3119.task_api.dtos.task.TaskDto
import my.denis3119.task_api.enums.TaskStatus
import my.denis3119.task_api.mapper.TaskMapper.toDto
import my.denis3119.task_api.mapper.TaskMapper.toEntity
import my.denis3119.task_api.models.Task
import my.denis3119.task_api.repositories.TaskRepository
import my.denis3119.task_api.services.TaskService
import my.denis3119.task_api.services.TeamMemberService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class TaskServiceImpl(
    private val taskRepository: TaskRepository,
    private val teamMemberService: TeamMemberService,
) : TaskService {

    @Transactional
    override fun createTask(createTaskDto: CreateTaskDto): TaskDto {
        val task = createTaskDto.toEntity()
        task.assignedTo = createTaskDto.assignedToId?.let { assignedId -> teamMemberService.findById(assignedId) }
        return save(task)
    }

    @Transactional
    override fun assignTask(taskId: Long, userId: Long): TaskDto {
        val task = findById(taskId).apply {
            assignedTo = teamMemberService.findById(userId)
        }
        return save(task)
    }

    @Transactional
    override fun updateTaskStatus(taskId: Long, newStatus: TaskStatus): TaskDto {
        val task = findById(taskId).apply {
            status = newStatus
        }
        fillDates(task, newStatus)
        return save(task)
    }

    override fun list(pageable: Pageable): Page<TaskDto> {
        return taskRepository.findAll(pageable).map { it.toDto() }
    }


    private fun save(task: Task) =
        taskRepository.save(task).also { println(it) }.toDto()


    override fun findById(taskId: Long): Task =
        taskRepository.findById(taskId)
            .orElseThrow { EntityNotFoundException("Task with id $taskId not found") }

    companion object {
        private fun fillDates(task: Task, newStatus: TaskStatus) {
            when (newStatus) {
                TaskStatus.IN_PROGRESS -> task.startDate = LocalDateTime.now()
                TaskStatus.COMPLETED -> {
                    task.startDate = task.startDate ?: LocalDateTime.now()
                    task.endDate = LocalDateTime.now()
                }

                else -> {
                }
            }
        }
    }
}
