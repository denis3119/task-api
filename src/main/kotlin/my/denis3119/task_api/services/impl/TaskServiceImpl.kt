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
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TaskServiceImpl(
    private val taskRepository: TaskRepository,
    private val teamMemberService: TeamMemberService
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
    override fun updateTaskStatus(taskId: Long, status: TaskStatus): TaskDto {
        val task = findById(taskId).apply {
            this.status = status
        }
        return save(task)
    }

    private fun save(task: Task) = taskRepository.save(task).toDto()

    private fun findById(taskId: Long): Task =
        taskRepository.findById(taskId)
            .orElseThrow { EntityNotFoundException("Task with id $taskId not found") }
}
