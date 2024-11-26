package my.denis3119.task_api.dtos.comment

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "Data Transfer Object for returning a comment")
data class CommentDto(

    @Schema(description = "ID of the comment", example = "1")
    val id: Long? = null,

    @Schema(description = "Text of the comment", example = "This is a comment")
    var text: String,

    @Schema(description = "ID of the associated task", example = "1")
    var taskId: Long,

    @Schema(description = "ID of the author", example = "1")
    var authorId: Long,

    @Schema(description = "Creation timestamp", example = "2024-11-26T12:34:56")
    var createdAt: LocalDateTime? = null
)
