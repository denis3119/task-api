package my.denis3119.task_api.controllers

import my.denis3119.task_api.dtos.task.CreateTaskDto
import my.denis3119.task_api.dtos.task.TaskDto
import my.denis3119.task_api.enums.TaskStatus
import my.denis3119.task_api.services.TaskService
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tasks")
@Validated
class TaskController(
    private val taskService: TaskService
) {
    @PostMapping
    @ResponseStatus(CREATED)
    fun createTask(@RequestBody createTaskDto: CreateTaskDto) = taskService.createTask(createTaskDto)

    @PutMapping("/{taskId}/assign/{userId}")
    @ResponseStatus(OK)
    fun assignTask(
        @PathVariable(name = "taskId") taskId: Long,
        @PathVariable(name = "userId") userId: Long
    ): TaskDto = taskService.assignTask(taskId, userId)

    @PatchMapping("/{taskId}/status")
    fun updateTaskStatus(
        @PathVariable taskId: Long,
        @RequestParam status: TaskStatus
    ): TaskDto = taskService.updateTaskStatus(taskId, status)



}


//

//
//    @GetMapping("/user/{userId}")
//    fun getTasksByUser(@PathVariable userId: Long): List<TaskDto> {
//        val tasks = taskService.getTasksByUser(userId)
//        return tasks.map { it.toDto() }
//    }
//
//    @GetMapping("/completed")
//    fun getCompletedTasks(
//        @RequestParam start: String,
//        @RequestParam end: String
//    ): List<TaskDto> {
//        val tasks = taskService.getTasksByPeriod(LocalDate.parse(start), LocalDate.parse(end))
//        return tasks.map { it.toDto() }
//    }
