package my.denis3119.task_api.services.impl

import my.denis3119.task_api.dtos.task.CreateTaskDto
import my.denis3119.task_api.dtos.task.TaskDto
import my.denis3119.task_api.mapper.TaskMapper.toDto
import my.denis3119.task_api.mapper.TaskMapper.toEntity
import my.denis3119.task_api.repositories.TaskRepository
import my.denis3119.task_api.services.TaskService
import org.springframework.stereotype.Service

@Service
class TaskServiceImpl(
    private val taskRepository: TaskRepository,
) : TaskService {
    override fun createTask(createTaskDto: CreateTaskDto): TaskDto {
        val task = createTaskDto.toEntity()
        return taskRepository.save(task).toDto()
    }
}
