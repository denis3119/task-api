package my.denis3119.task_api.dtos.comment

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Data Transfer Object for creating a comment")
data class CreateCommentDto(

    @Schema(description = "Text of the comment", example = "This is a comment")
    val text: String,

    @Schema(description = "ID of the associated task", example = "123")
    val taskId: Long
)
