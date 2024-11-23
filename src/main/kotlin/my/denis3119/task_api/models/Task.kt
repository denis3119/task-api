package my.denis3119.task_api.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import my.denis3119.task_api.enums.TaskPriority
import my.denis3119.task_api.enums.TaskPriority.MEDIUM
import my.denis3119.task_api.enums.TaskStatus
import my.denis3119.task_api.enums.TaskStatus.NEW
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
data class Task(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, name = "TITLE")
    var title: String,

    @Column(nullable = false, name = "DESCRIPTION")
    var description: String,

    @Column(nullable = false, name = "START_DATE")
    var startDate: LocalDateTime? = null,

    @Column(nullable = false, name = "END_DATE")
    var endDate: LocalDateTime? = null,

    @Column(nullable = false, name = "DUE_DATE")
    var dueDate: LocalDateTime,

    @Column(nullable = false, name = "PRIORITY")
    @Enumerated(STRING)
    var priority: TaskPriority = MEDIUM,

    @Column(nullable = false, name = "STATUS")
    @Enumerated(STRING)
    var status: TaskStatus = NEW,

    @ManyToOne
    @JoinColumn(name = "ASSIGNED_TO")
    var assignedTo: TeamMember? = null,

    @Column(nullable = false, name = "CREATED_ON")
    @CreatedDate
    val createdOn: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false, name = "LAST_MODIFIED")
    @LastModifiedDate
    val lastModified: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "CREATED_BY")
    val createdBy: TeamMember,

    @ManyToOne
    @JoinColumn(name = "LAST_MODIFIED_BY")
    val lastModifiedBy: TeamMember
)
