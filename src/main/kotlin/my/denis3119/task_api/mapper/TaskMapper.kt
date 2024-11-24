package my.denis3119.task_api.mapper

import my.denis3119.task_api.dtos.task.CreateTaskDto
import my.denis3119.task_api.dtos.task.TaskDto
import my.denis3119.task_api.mapper.CommentMapper.toDto
import my.denis3119.task_api.mapper.TeamMemberMapper.toDto
import my.denis3119.task_api.models.Task

object TaskMapper {

    fun CreateTaskDto.toEntity() = Task(
        title = title,
        description = description,
        dueDate = dueDate,
        priority = priority,
        status = status,
    )

    fun Task.toDto() = TaskDto(
        id = id,
        title = title,
        description = description,
        startDate = startDate,
        endDate = endDate,
        dueDate = dueDate,
        priority = priority,
        status = status,
        assignedTo = assignedTo?.toDto(),
        comments = comments.map { it.toDto() }
    )
}
