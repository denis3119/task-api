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

        userId = teamMemberRepository.save(TeamMember(name = "admin", password = "test")).id
    }

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
    fun assignTask_positiveFlow() {
        createTask_positiveFlowMinimaParameters()

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
    fun updateTaskStatus_positiveFlow() {
        createTask_positiveFlowMinimaParameters()

        val task = taskRepository.findAll().first()

        assertEquals(NEW, task.status)
        assertNull(task.startDate)
        assertNotNull(task.createdOn)
        assertNotNull(task.lastModified)

        var taskDto = changeStatus(task, IN_PROGRESS)

        assertEquals(IN_PROGRESS, taskDto.status)
        assertNotNull(taskDto.startDate)

        taskDto = changeStatus(task, COMPLETED)

        assertEquals(COMPLETED, taskDto.status)
        assertNotNull(taskDto.endDate)
    }

    /*
    *     @GetMapping("/list")
    fun list(
        @PageableDefault(size = 20)
        @SortDefault(value = ["createdOn"], direction = DESC) pageable: Pageable
    ): Page<TaskDto> = taskService.list(pageable)*/

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


    private fun changeStatus(task: Task, status: TaskStatus): TaskDto {
        return mockMvc.perform(
            patch("/tasks/{taskId}/status", task.id)
                .param("status", status.name)
        )
            .andDo { print() }
            .andExpect(status().isOk)
            .andReturn().response.contentAsString
            .let { objectMapper.readValue(it, TaskDto::class.java) }
    }
}
