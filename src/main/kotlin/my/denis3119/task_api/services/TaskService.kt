package my.denis3119.task_api.services

import my.denis3119.task_api.dtos.task.CreateTaskDto
import my.denis3119.task_api.dtos.task.TaskDto
import my.denis3119.task_api.enums.TaskStatus
import my.denis3119.task_api.models.Task
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface TaskService {
    fun createTask(createTaskDto: CreateTaskDto): TaskDto
    fun assignTask(taskId: Long, userId: Long): TaskDto
    fun updateTaskStatus(taskId: Long, newStatus: TaskStatus): TaskDto
    fun list(pageable: Pageable): Page<TaskDto>
    fun findById(taskId: Long): Task
}
