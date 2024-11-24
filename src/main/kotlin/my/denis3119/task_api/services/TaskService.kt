package my.denis3119.task_api.services

import my.denis3119.task_api.dtos.task.CreateTaskDto
import my.denis3119.task_api.dtos.task.TaskDto
import my.denis3119.task_api.enums.TaskStatus

interface TaskService {
    fun createTask(createTaskDto: CreateTaskDto): TaskDto
    fun assignTask(taskId: Long, userId: Long): TaskDto
    fun updateTaskStatus(taskId: Long, status: TaskStatus): TaskDto
}
