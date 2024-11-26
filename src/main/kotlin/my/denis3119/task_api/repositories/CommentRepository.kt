package my.denis3119.task_api.repositories

import my.denis3119.task_api.models.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {
    fun findAllByTaskId(taskId: Long): List<Comment>
}
