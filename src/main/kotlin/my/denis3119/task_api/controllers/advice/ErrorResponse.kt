package my.denis3119.task_api.controllers.advice

data class ErrorResponse(
    val status: Int,
    val error: String,
    val message: String
)
