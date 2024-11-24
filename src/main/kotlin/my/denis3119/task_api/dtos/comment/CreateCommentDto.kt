package my.denis3119.task_api.dtos.comment

data class CreateCommentDto(
    val text: String,
    val taskId: Long
)
