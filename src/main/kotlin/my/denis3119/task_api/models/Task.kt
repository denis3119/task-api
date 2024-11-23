package my.denis3119.task_api.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import my.denis3119.task_api.enums.TaskPriority
import my.denis3119.task_api.enums.TaskStatus
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDate

@Entity
data class Task(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, name = "TITLE")
    var title: String,

    @Column(nullable = false, name = "DESCRIPTION")
    var description: String,

    @Column(nullable = false, name = "START_DATE")
    var startDate: LocalDate? = null,

    @Column(nullable = false, name = "END_DATE")
    var endDate: LocalDate? = null,

    @Column(nullable = false, name = "DUE_DATE")
    var dueDate: LocalDate,

    @Column(nullable = false, name = "PRIORITY")
    @Enumerated(STRING)
    var priority: TaskPriority,

    @Column(nullable = false, name = "STATUS")
    var status: TaskStatus = TaskStatus.NEW,

    @ManyToOne
    var assignedTo: TeamMember? = null,

    @Column(nullable = false, name = "CREATED_ON")
    @CreatedDate
    val createdOn: LocalDate = LocalDate.now(),

    @Column(nullable = false, name = "CREATED_ON")
    @LastModifiedDate
    val lastModified: LocalDate = LocalDate.now()
)
