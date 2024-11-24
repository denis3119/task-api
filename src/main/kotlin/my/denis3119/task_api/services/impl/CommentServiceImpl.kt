package my.denis3119.task_api.services.impl

import my.denis3119.task_api.dtos.comment.CreateCommentDto
import my.denis3119.task_api.models.Comment
import my.denis3119.task_api.repositories.CommentRepository
import my.denis3119.task_api.services.CommentService
import my.denis3119.task_api.services.TaskService
import org.springframework.stereotype.Service

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val taskService: TaskService
) : CommentService {
    override fun create(createCommentDto: CreateCommentDto) {
        Comment(
            text = createCommentDto.text,
            task = taskService.findById(createCommentDto.taskId)
        ).also {
            commentRepository.save(it)
        }
    }
}
