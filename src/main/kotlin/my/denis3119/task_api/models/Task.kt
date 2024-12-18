package my.denis3119.task_api.models

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import my.denis3119.task_api.enums.TaskPriority
import my.denis3119.task_api.enums.TaskPriority.MEDIUM
import my.denis3119.task_api.enums.TaskStatus
import my.denis3119.task_api.enums.TaskStatus.NEW
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "TASK")
@EntityListeners(AuditingEntityListener::class)
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TASK_SEQ")
    @SequenceGenerator(name = "TASK_SEQ", sequenceName = "TASK_SEQ", allocationSize = 1)
    @Column(name = "ID")
    val id: Long? = null,

    @Column(nullable = false, name = "TITLE")
    var title: String,

    @Column(nullable = false, name = "DESCRIPTION")
    var description: String,

    @Column(name = "START_DATE")
    var startDate: LocalDateTime? = null,

    @Column(name = "END_DATE")
    var endDate: LocalDateTime? = null,

    @Column(name = "DUE_DATE")
    var dueDate: LocalDateTime? = null,

    @Column(nullable = false, name = "PRIORITY")
    @Enumerated(STRING)
    var priority: TaskPriority = MEDIUM,

    @Column(nullable = false, name = "STATUS")
    @Enumerated(STRING)
    var status: TaskStatus = NEW,

    @ManyToOne
    @JoinColumn(name = "ASSIGNED_TO")
    var assignedTo: TeamMember? = null,

    @OneToMany(mappedBy = "task", orphanRemoval = true, cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var comments: MutableList<Comment> = mutableListOf(),

    @Column(nullable = false, name = "CREATED_ON")
    @CreatedDate
    val createdOn: LocalDateTime? = LocalDateTime.now(),

    @Column(nullable = false, name = "LAST_MODIFIED")
    @LastModifiedDate
    val lastModified: LocalDateTime? = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "CREATED_BY")
    val createdBy: TeamMember? = null,

    @ManyToOne
    @JoinColumn(name = "LAST_MODIFIED_BY")
    val lastModifiedBy: TeamMember? = null
)
