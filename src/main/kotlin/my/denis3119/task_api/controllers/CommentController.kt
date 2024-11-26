package my.denis3119.task_api.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import my.denis3119.task_api.dtos.comment.CommentDto
import my.denis3119.task_api.dtos.comment.CreateCommentDto
import my.denis3119.task_api.services.CommentService
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody

@RestController
@RequestMapping("/comments")
@Validated
class CommentController(
    private val commentService: CommentService
) {

    @Operation(
        summary = "Create a new comment",
        description = "Creates a comment for a specific task.",
        requestBody = SwaggerRequestBody(
            description = "Comment creation details",
            required = true,
            content = [Content(schema = Schema(implementation = CreateCommentDto::class))]
        ),
        responses = [
            ApiResponse(responseCode = "201", description = "Comment created successfully"),
            ApiResponse(responseCode = "400", description = "Invalid input")
        ]
    )
    @PostMapping
    @ResponseStatus(CREATED)
    fun createComment(
        @RequestBody createCommentDto: CreateCommentDto
    ) {
        commentService.create(createCommentDto)
    }

    @Operation(
        summary = "Get comments by task ID",
        description = "Retrieves a list of comments associated with a specific task ID.",
        responses = [
            ApiResponse(
                responseCode = "200", description = "Comments retrieved successfully", content = [
                    Content(schema = Schema(implementation = CommentDto::class, type = "array"))
                ]
            ),
            ApiResponse(responseCode = "404", description = "Task not found")
        ]
    )
    @GetMapping("/list")
    @ResponseStatus(OK)
    fun createComment(
        @RequestParam taskId: Long
    ): List<CommentDto> = commentService.list(taskId)
}
