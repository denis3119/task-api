package my.denis3119.task_api.mapper

import my.denis3119.task_api.dtos.comment.CommentDto
import my.denis3119.task_api.models.Comment

object CommentMapper {
    fun Comment.toDto() = CommentDto(
        id = id,
        text = text,
        authorId = author?.id ?: throw IllegalStateException("Author id is null"),
        taskId = task.id ?: throw IllegalStateException("Task id is null"),
        createdAt = createdAt
    )
}
