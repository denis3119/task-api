package my.denis3119.task_api.utils

import java.time.LocalDate
import java.time.LocalDateTime

object DateUtils {
    fun LocalDate.atEndOfDay(): LocalDateTime = this.atStartOfDay().plusDays(1)
}
