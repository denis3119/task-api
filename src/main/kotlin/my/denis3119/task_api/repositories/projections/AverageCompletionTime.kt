package my.denis3119.task_api.repositories.projections

interface AverageCompletionTime {
    val totalTaskCount: Long
    val averageTimeInMinutes: Long
}
