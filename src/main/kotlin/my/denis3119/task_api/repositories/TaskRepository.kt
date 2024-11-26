package my.denis3119.task_api.repositories

import my.denis3119.task_api.models.Task
import my.denis3119.task_api.repositories.projections.AverageCompletionTime
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface TaskRepository : JpaRepository<Task, Long> {

    @Query(
        """
        SELECT    
            count(*) as totalTaskCount,
            coalesce(AVG(EXTRACT(EPOCH FROM end_date - start_date) / 3600), 0) as averageTimeInMinutes
        FROM task as t
        WHERE t.end_date BETWEEN :from AND :to
        AND t.status = 'COMPLETED'
        AND (:assignedTo IS NULL OR t.ASSIGNED_TO = :assignedTo)
        """,
        nativeQuery = true
    )
    fun getAverageCompletionTimeInMinutes(
        from: LocalDateTime,
        to: LocalDateTime,
        assignedTo: Long? = null
    ): AverageCompletionTime

}
