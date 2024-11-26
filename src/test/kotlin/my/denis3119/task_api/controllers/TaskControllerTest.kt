package my.denis3119.task_api.controllers

import com.fasterxml.jackson.core.type.TypeReference
import my.denis3119.task_api.AbstractControllerTest
import my.denis3119.task_api.dtos.task.TaskDto
import my.denis3119.task_api.enums.TaskPriority.HIGH
import my.denis3119.task_api.enums.TaskStatus
import my.denis3119.task_api.enums.TaskStatus.COMPLETED
import my.denis3119.task_api.enums.TaskStatus.IN_PROGRESS
import my.denis3119.task_api.enums.TaskStatus.NEW
import my.denis3119.task_api.models.Task
import my.denis3119.task_api.models.TeamMember
import my.denis3119.task_api.repositories.TaskRepository
import my.denis3119.task_api.repositories.TeamMemberRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@WithMockUser(roles = ["ADMIN"], username = "admin", password = "admin")
class TaskControllerTest : AbstractControllerTest() {

    @Autowired
    private lateinit var taskRepository: TaskRepository

    @Autowired
    private lateinit var teamMemberRepository: TeamMemberRepository

    private var userId: Long? = null

    @BeforeEach
    fun setUp() {
        taskRepository.deleteAll()
        teamMemberRepository.deleteAll()

        userId = teamMemberRepository.save(TeamMember(name = "admin", password = "admin")).id
    }

    @Test
    fun createTask_positiveFlowWithMinimalParameters() {
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

        val task = taskRepository.findAll().first()

        assertEquals("Test task", task.title)
        assertEquals("Test description", task.description)
        assertEquals(HIGH, task.priority)
        assertNull(task.assignedTo)
        assertEquals(NEW, task.status)
        assertNull(task.dueDate)
        assertNotNull(task.id)
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
                      "assignedToId": $userId,
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
        assertEquals(userId, tasks.assignedTo?.id)
        assertEquals(NEW, tasks.status)
        assertNotNull(tasks.id)
    }

    @Test
    @Transactional
    fun assignTask_positiveFlow() {
        createTasks(1)

        val task = taskRepository.findAll().first()
        assertNull(task.assignedTo)

        val taskDto = mockMvc.perform(
            post("/tasks/{taskId}/assign/{userId}", task.id, userId)
        )
            .andDo { print() }
            .andExpect(status().isOk)
            .andReturn().response.contentAsString
            .let { objectMapper.readValue(it, TaskDto::class.java) }

        assertNotNull(taskDto.assignedTo)
        assertEquals(userId, taskDto.assignedTo?.id)
    }

    @Test
    @Transactional
    fun assignTask_notFoundTask() {
        mockMvc.perform(
            post("/tasks/{taskId}/assign/{userId}", 100500, userId)
        )
            .andDo { print() }
            .andExpect(status().isNotFound)
    }


    @Test
    @Transactional
    fun updateTaskStatus_positiveFlow() {
        createTask_positiveFlowWithMinimalParameters()

        val task = taskRepository.findAll().first()

        assertEquals(NEW, task.status)
        assertNull(task.startDate)
        assertNotNull(task.createdOn)
        assertNotNull(task.lastModified)

        var taskDto = changeStatus(task.id ?: 0, IN_PROGRESS)

        assertEquals(IN_PROGRESS, taskDto.status)
        assertNotNull(taskDto.startDate)

        taskDto = changeStatus(taskDto.id ?: 0, COMPLETED)

        assertEquals(COMPLETED, taskDto.status)
        assertNotNull(taskDto.endDate)
    }

    @Test
    fun list() {
        val taskCount = 100L

        createTasks(taskCount)

        val result = mockMvc.perform(
            get("/tasks/list")
        )
            .andDo { print() }
            .andExpect(status().isOk)
            .andReturn().response.contentAsByteArray
            .let { objectMapper.readValue(it, object : TypeReference<Page<TaskDto>>() {}) }

        assertEquals(taskCount, result.totalElements)
        assertEquals(20, result.size)
        assertEquals(20, result.content.size)
    }

    private fun createTasks(taskCount: Long) {
        for (i in 1..taskCount) {
            taskRepository.save(Task(title = "Task $i", description = "Description $i", priority = HIGH, status = NEW))
        }
    }


    private fun changeStatus(id: Long, status: TaskStatus): TaskDto {
        return mockMvc.perform(
            patch("/tasks/{taskId}/status", id)
                .param("status", status.name)
        )
            .andDo { print() }
            .andExpect(status().isOk)
            .andReturn().response.contentAsString
            .let { objectMapper.readValue(it, TaskDto::class.java) }
    }
}
