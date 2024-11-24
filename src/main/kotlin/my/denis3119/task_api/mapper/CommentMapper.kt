package my.denis3119.task_api.mapper

import my.denis3119.task_api.dtos.task.CommentDto
import my.denis3119.task_api.mapper.TeamMemberMapper.toDto
import my.denis3119.task_api.models.Comment

object CommentMapper {
    fun Comment.toDto() = CommentDto(
        id = id,
        text = text,
        author = author.toDto(),
        taskId = task.id ?: throw IllegalStateException("Task id is null"),
        createdAt = createdAt
    )
}
