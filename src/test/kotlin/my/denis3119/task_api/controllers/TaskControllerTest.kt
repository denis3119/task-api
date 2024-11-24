package my.denis3119.task_api.controllers

import my.denis3119.task_api.AbstractControllerTest
import my.denis3119.task_api.dtos.task.TaskDto
import my.denis3119.task_api.enums.TaskPriority.HIGH
import my.denis3119.task_api.enums.TaskStatus.IN_PROGRESS
import my.denis3119.task_api.enums.TaskStatus.NEW
import my.denis3119.task_api.repositories.TaskRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@WithMockUser(roles = ["ADMIN"], username = "admin", password = "admin")
class TaskControllerTest : AbstractControllerTest() {

    @Autowired
    private lateinit var taskRepository: TaskRepository

    @Test
    fun createTask_positiveFlowMinimaParameters() {
        mockMvc.perform(
            post("/tasks")
                .contentType("application/json")
                .content(
                    """
                    {
                      "title": "Test task",
                      "description": "Test description",
                      "priority": "HIGH",
                      "status": "NEW"
                    }
                  """.trimIndent()
                )
        )
            .andDo { print() }
            .andExpect(status().isCreated)

        val tasks = taskRepository.findAll().first()

        assertEquals("Test task", tasks.title)
        assertEquals("Test description", tasks.description)
        assertEquals(HIGH, tasks.priority)
        assertNull(tasks.assignedTo)
        assertEquals(NEW, tasks.status)
        assertNull(tasks.dueDate)
        assertNotNull(tasks.id)
    }

    @Test
    fun createTask_positiveFlowAllParameters() {
        mockMvc.perform(
            post("/tasks")
                .contentType("application/json")
                .content(
                    """
                    {
                      "title": "Test task",
                      "description": "Test description",
                      "priority": "HIGH",
                      "status": "NEW",
                      "assignedToId": 1,
                      "dueDate": "2025-12-12T12:12:12"
                    }
                  """.trimIndent()
                )
        )
            .andDo { print() }
            .andExpect(status().isCreated)

        val tasks = taskRepository.findAll().first()

        assertEquals("Test task", tasks.title)
        assertEquals("Test description", tasks.description)
        assertEquals(HIGH, tasks.priority)
        assertEquals(LocalDateTime.of(2025, 12, 12, 12, 12, 12), tasks.dueDate)
        assertEquals(1, tasks.assignedTo?.id)
        assertEquals(NEW, tasks.status)
        assertNotNull(tasks.id)
    }

    @Test
    fun assignTask_positiveFlow() {
        createTask_positiveFlowMinimaParameters()

        val task = taskRepository.findAll().first()

        assertNull(task.assignedTo)

        val taskDto = mockMvc.perform(
            post("/tasks/{taskId}/assign/{userId}", task.id, 1)
        )
            .andDo { print() }
            .andExpect(status().isOk)
            .andReturn().response.contentAsString
            .let { objectMapper.readValue(it, TaskDto::class.java) }

        assertNotNull(taskDto.assignedTo)
        assertEquals(1, taskDto.assignedTo?.id)
    }

    /*
    *    @PatchMapping("/{taskId}/status")
    fun updateTaskStatus(
        @PathVariable taskId: Long,
        @RequestParam status: TaskStatus
    ): TaskDto = taskService.updateTaskStatus(taskId, status)*/

    @Test
    fun updateTaskStatus_positiveFlow() {
        createTask_positiveFlowMinimaParameters()

        val task = taskRepository.findAll().first()

        assertEquals(NEW, task.status)

        val taskDto = mockMvc.perform(
            patch("/tasks/{taskId}/status", task.id)
                .param("status", IN_PROGRESS.name)
        )
            .andDo { print() }
            .andExpect(status().isOk)
            .andReturn().response.contentAsString
            .let { objectMapper.readValue(it, TaskDto::class.java) }

        assertEquals(IN_PROGRESS, taskDto.status)
    }
}
