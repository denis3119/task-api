package my.denis3119.task_api.services

import my.denis3119.task_api.dtos.task.CreateTaskDto
import my.denis3119.task_api.dtos.task.TaskDto

interface TaskService {
    fun createTask(createTaskDto: CreateTaskDto): TaskDto
}
