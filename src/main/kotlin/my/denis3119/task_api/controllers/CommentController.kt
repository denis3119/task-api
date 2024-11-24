package my.denis3119.task_api.controllers

import my.denis3119.task_api.dtos.comment.CreateCommentDto
import my.denis3119.task_api.services.CommentService
import org.springframework.http.HttpStatus.CREATED
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/comments")
@Validated
class CommentController(
    private val commentService: CommentService
) {

    @PostMapping
    @ResponseStatus(CREATED)
    fun createComment(
        @RequestBody createCommentDto: CreateCommentDto
    ) {
        commentService.create(createCommentDto)
    }

}
