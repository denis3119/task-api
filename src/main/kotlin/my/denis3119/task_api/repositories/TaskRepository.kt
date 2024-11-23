package my.denis3119.task_api.repositories

import my.denis3119.task_api.models.Task
import org.springframework.data.jpa.repository.JpaRepository

interface TaskRepository : JpaRepository<Task, Long>
