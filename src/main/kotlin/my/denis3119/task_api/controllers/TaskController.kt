package my.denis3119.task_api.controllers

import my.denis3119.task_api.dtos.task.CreateTaskDto
import my.denis3119.task_api.services.TaskService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tasks")
@Validated
class TaskController(
    private val taskService: TaskService
) {
    @PostMapping
    fun createTask(@RequestBody createTaskDto: CreateTaskDto) = taskService.createTask(createTaskDto)

}

//    @PutMapping("/{taskId}/assign/{userId}")
//    fun assignTask(
//        @PathVariable taskId: Long,
//        @PathVariable userId: Long
//    ): TaskDto {
//        val task = taskService.assignTask(taskId, userId)
//        return task.toDto()
//    }
//
//    @PatchMapping("/{taskId}/status")
//    fun updateTaskStatus(
//        @PathVariable taskId: Long,
//        @RequestParam status: String
//    ): TaskDto {
//        val task = taskService.updateTaskStatus(taskId, status)
//        return task.toDto()
//    }
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
