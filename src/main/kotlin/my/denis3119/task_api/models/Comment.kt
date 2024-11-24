package my.denis3119.task_api.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.SEQUENCE
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "COMMENT")
@EntityListeners(AuditingEntityListener::class)
data class Comment(
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "COMMENT_SEQ")
    @SequenceGenerator(name = "COMMENT_SEQ", sequenceName = "COMMENT_SEQ", allocationSize = 1)
    @Column(name = "ID")
    val id: Long? = null,

    @Column(nullable = false, name = "TEXT")
    var text: String,

    @ManyToOne
    @JoinColumn(name = "TASK_ID")
    var task: Task,

    @ManyToOne
    @JoinColumn(name = "AUTHOR_ID")
    @CreatedBy
    var author: TeamMember? = null,

    @Column(name = "CREATED_AT")
    @CreatedDate
    var createdAt: LocalDateTime? = null
)
