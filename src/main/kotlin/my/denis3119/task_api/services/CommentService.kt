package my.denis3119.task_api.services

import my.denis3119.task_api.dtos.comment.CommentDto
import my.denis3119.task_api.dtos.comment.CreateCommentDto

interface CommentService {
    fun create(createCommentDto: CreateCommentDto)
    fun list(taskId: Long): List<CommentDto>
}
