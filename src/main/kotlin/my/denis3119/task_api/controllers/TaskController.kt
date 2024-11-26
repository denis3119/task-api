package my.denis3119.task_api.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import my.denis3119.task_api.dtos.task.CreateTaskDto
import my.denis3119.task_api.dtos.task.TaskDto
import my.denis3119.task_api.enums.TaskStatus
import my.denis3119.task_api.services.TaskService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort.Direction.DESC
import org.springframework.data.web.PageableDefault
import org.springframework.data.web.SortDefault
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody

@RestController
@RequestMapping("/tasks")
@Validated
class TaskController(
    private val taskService: TaskService
) {

    @Operation(
        summary = "Create a new task",
        description = "Creates a new task with the provided details.",
        requestBody = SwaggerRequestBody(
            description = "Details of the task to create",
            required = true,
            content = [Content(schema = Schema(implementation = CreateTaskDto::class))]
        ),
        responses = [
            ApiResponse(responseCode = "201", description = "Task created successfully"),
            ApiResponse(responseCode = "400", description = "Invalid input")
        ]
    )
    @PostMapping
    @ResponseStatus(CREATED)
    fun createTask(@RequestBody createTaskDto: CreateTaskDto) = taskService.createTask(createTaskDto)

    @Operation(
        summary = "Assign a user to a task",
        description = "Assigns a task to a specific user.",
        responses = [
            ApiResponse(
                responseCode = "200", description = "Task assigned successfully", content = [
                    Content(schema = Schema(implementation = TaskDto::class))
                ]
            ),
            ApiResponse(responseCode = "404", description = "Task or user not found")
        ]
    )
    @PostMapping("/{taskId}/assign/{userId}")
    @ResponseStatus(OK)
    fun assignTask(
        @PathVariable(name = "taskId") taskId: Long,
        @PathVariable(name = "userId") userId: Long
    ): TaskDto = taskService.assignTask(taskId, userId)

    @Operation(
        summary = "Update task status",
        description = "Updates the status of a task.",
        responses = [
            ApiResponse(
                responseCode = "200", description = "Task status updated", content = [
                    Content(schema = Schema(implementation = TaskDto::class))
                ]
            ),
            ApiResponse(responseCode = "404", description = "Task not found")
        ]
    )
    @PatchMapping("/{taskId}/status")
    fun updateTaskStatus(
        @PathVariable taskId: Long,
        @RequestParam status: TaskStatus
    ): TaskDto = taskService.updateTaskStatus(taskId, status)

    @Operation(
        summary = "List tasks",
        description = "Retrieves a paginated list of tasks.",
        responses = [
            ApiResponse(
                responseCode = "200", description = "Tasks retrieved successfully", content = [
                    Content(schema = Schema(implementation = Page::class)),
                    Content(schema = Schema(implementation = TaskDto::class))
                ]
            )
        ]
    )
    @GetMapping("/list")
    fun list(
        @PageableDefault(size = 20)
        @SortDefault(value = ["createdOn"], direction = DESC) pageable: Pageable
    ): Page<TaskDto> = taskService.list(pageable)
}
