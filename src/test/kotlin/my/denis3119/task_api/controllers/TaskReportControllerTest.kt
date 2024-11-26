package my.denis3119.task_api.controllers

import my.denis3119.task_api.AbstractControllerTest
import my.denis3119.task_api.enums.TaskPriority.HIGH
import my.denis3119.task_api.enums.TaskStatus.COMPLETED
import my.denis3119.task_api.models.Task
import my.denis3119.task_api.models.TeamMember
import my.denis3119.task_api.repositories.TaskRepository
import my.denis3119.task_api.repositories.TeamMemberRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@WithMockUser(roles = ["ADMIN"], username = "admin", password = "admin")
class TaskReportControllerTest : AbstractControllerTest() {
    @Autowired
    private lateinit var taskRepository: TaskRepository

    @Autowired
    private lateinit var teamMemberRepository: TeamMemberRepository

    private lateinit var user: TeamMember

    @BeforeEach
    fun setUp() {
        user = teamMemberRepository.findAll().first()

        taskRepository.deleteAll()
        createCompletedTasks(10)
    }

    @Test
    fun generateReport() {
        mockMvc.perform(
            get("/reports/tasks/general/generate")
                .param("from", "2000-01-01")
                .param("to", "2090-12-31")
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(
                content().json(
                    """
                    {
                      "from": "2000-01-01",
                      "to": "2090-12-31",
                      "completed": 10,
                      "averageCompletionTimeInMinutes": 480
                    }
                    """.trimIndent(), true
                )
            )
    }

    @Test
    fun generatePersonalReport_userExists() {
        mockMvc.perform(
            get("/reports/tasks/personal/generate")
                .param("from", "2000-01-01")
                .param("to", "2090-12-31")
                .param("teamMemberId", user.id.toString())
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(
                content().json(
                    """
                    {
                      "from": "2000-01-01",
                      "to": "2090-12-31",
                      "teamMemberId": ${user.id},
                      "completed": 10,
                      "averageCompletionTimeInMinutes": 480
                    }
                """.trimIndent(), true
                )
            )
    }

    @Test
    fun generatePersonalReport_userNotExists() {
        mockMvc.perform(
            get("/reports/tasks/personal/generate")
                .param("from", "2000-01-01")
                .param("to", "2090-12-31")
                .param("teamMemberId", "100500")
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(
                content().json(
                    """
                   {
                     "from": "2000-01-01",
                     "to": "2090-12-31",
                     "teamMemberId": 100500,
                     "completed": 0,
                     "averageCompletionTimeInMinutes": 0
                   }
                """.trimIndent(), true
                )
            )
    }

    private fun createCompletedTasks(taskCount: Long) {
        repeat(taskCount.toInt()) { i ->
            taskRepository.save(
                Task(
                    title = "Task ${i + 1}",
                    description = "Description ${i + 1}",
                    priority = HIGH,
                    status = COMPLETED,
                    startDate = LocalDateTime.now().minusDays(taskCount),
                    endDate = LocalDateTime.now().plusDays(taskCount),
                    assignedTo = user
                )
            )
        }
    }
}
