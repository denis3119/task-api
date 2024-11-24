package my.denis3119.task_api.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.SEQUENCE
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import my.denis3119.task_api.enums.UserRole
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Table(name = "TEAM_MEMBER")
@EntityListeners(AuditingEntityListener::class)
data class TeamMember(
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "TEAM_MEMBER_SEQ")
    @SequenceGenerator(name = "TEAM_MEMBER_SEQ", sequenceName = "TEAM_MEMBER_SEQ", allocationSize = 1)
    @Column(name = "ID")
    val id: Long? = null,

    @Column(nullable = false, name = "USERNAME", unique = true)
    val name: String,

    @Column(nullable = false, name = "PASSWORD")
    val password: String,

    @Column(nullable = false, name = "ROLE")
    @Enumerated(STRING)
    val role: UserRole = UserRole.USER
)
