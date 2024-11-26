package my.denis3119.task_api.controllers

import com.fasterxml.jackson.core.type.TypeReference
import my.denis3119.task_api.AbstractControllerTest
import my.denis3119.task_api.dtos.comment.CommentDto
import my.denis3119.task_api.enums.TaskPriority.HIGH
import my.denis3119.task_api.enums.TaskStatus.NEW
import my.denis3119.task_api.models.Comment
import my.denis3119.task_api.models.Task
import my.denis3119.task_api.models.TeamMember
import my.denis3119.task_api.repositories.CommentRepository
import my.denis3119.task_api.repositories.TaskRepository
import my.denis3119.task_api.repositories.TeamMemberRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@WithMockUser(roles = ["ADMIN"], username = "admin", password = "admin")
class CommentControllerTest : AbstractControllerTest() {

    @Autowired
    private lateinit var taskRepository: TaskRepository

    @Autowired
    private lateinit var commentRepository: CommentRepository

    @Autowired
    private lateinit var teamMemberRepository: TeamMemberRepository

    @BeforeEach
    fun setUp() {
        commentRepository.deleteAll()
        taskRepository.deleteAll()
        teamMemberRepository.deleteAll()
        teamMemberRepository.save(TeamMember(name = "admin", password = "admin"))
    }

    @Test
    fun createComment_positiveFlow() {
        createTasks(10)

        var task = taskRepository.findAll().first()

        mockMvc.perform(
            post("/comments")
                .contentType(APPLICATION_JSON)
                .content(
                    """
                        {
                          "text": "Test comment",
                          "taskId": ${task.id}
                        }
                     """.trimIndent()
                )
        )
            .andDo { print() }
            .andExpect(status().isCreated)

        val comment = commentRepository.findAll().first()
        task = taskRepository.findById(task.id ?: 0).get()

        assertEquals("Test comment", comment.text)
        assertEquals(task.id, comment.task.id)
        assertNotNull(comment.author)
        assertEquals("admin", comment.author?.name)

        assertEquals(1, task.comments.size)
        assertEquals("Test comment", task.comments.first().text)
    }

    @Test
    fun listComments_positiveFlow() {
        createTasks(10)

        val task = taskRepository.findAll().first()

        createComments(task)

        val commentDtos = mockMvc.perform(
            get("/comments/list")
                .param("taskId", task.id.toString())
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andReturn().response.contentAsString.let {
                objectMapper.readValue(it, object : TypeReference<List<CommentDto>>() {})
            }

        assertEquals(10, commentDtos.size)
    }

    private fun createComments(task: Task) {
        for (i in 1..10) {
            commentRepository.save(Comment(text = "Comment $i", task = task))
        }
    }


    private fun createTasks(taskCount: Long) {
        for (i in 1..taskCount) {
            taskRepository.save(Task(title = "Task $i", description = "Description $i", priority = HIGH, status = NEW))
        }
    }
}
