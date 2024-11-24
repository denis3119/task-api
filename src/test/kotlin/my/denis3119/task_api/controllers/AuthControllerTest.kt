package my.denis3119.task_api.controllers

import my.denis3119.task_api.AbstractControllerTest
import my.denis3119.task_api.configs.JwtUtil
import my.denis3119.task_api.dtos.auth.TokenDto
import my.denis3119.task_api.repositories.TaskRepository
import my.denis3119.task_api.repositories.TeamMemberRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@WithMockUser(roles = ["ADMIN"])
class AuthControllerTest : AbstractControllerTest() {


    @Autowired
    private lateinit var teamMemberRepository: TeamMemberRepository

    @Autowired
    private lateinit var taskRepository: TaskRepository

    @BeforeEach
    fun beforeEach() {
        taskRepository.deleteAll()
        teamMemberRepository.deleteAll()
    }

    @Test
    fun registerUser_positiveFlow() {
        mockMvc.perform(
            post("/auth/register")
                .contentType(APPLICATION_JSON)
                .content(
                    testUserJson
                )
        )
            .andDo { print() }
            .andExpect(status().isCreated)


        assertEquals(1L, teamMemberRepository.count())
        val teamMember = teamMemberRepository.findAll().first()

        assertEquals("test", teamMember.name)
        assertTrue { BCryptPasswordEncoder().matches("test", teamMember.password) }
    }

    @Test
    fun registerUser_nullBody() {
        mockMvc.perform(
            post("/auth/register")
                .contentType(APPLICATION_JSON)
        )
            .andDo { print() }
            .andExpect(status().isBadRequest)

    }

    @Test
    fun registerUser_noName() {
        mockMvc.perform(
            post("/auth/register")
                .contentType(APPLICATION_JSON)
                .content(
                    """
                    {
                        "password": "test"
                    }
                    """.trimIndent()
                )
        )
            .andDo { print() }
            .andExpect(status().isBadRequest)

    }

    @Test
    fun login() {
        registerUser_positiveFlow()

        val tokenResponse = mockMvc.perform(
            post("/auth/login")
                .contentType(APPLICATION_JSON)
                .content(
                    testUserJson
                )
        )
            .andDo { print() }
            .andExpect(status().isOk)
            .andReturn().response.contentAsString

        val tokenDto = objectMapper.readValue(tokenResponse, TokenDto::class.java)

        assertTrue { tokenDto.token.isNotBlank() }
        assertTrue { JwtUtil.isTokenValid(tokenDto.token, "test") }

    }

    companion object {
        val testUserJson =
            """
            {
                "username": "test",
                "password": "test"
            }
            """.trimIndent()

    }
}
