package my.denis3119.task_api.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import my.denis3119.task_api.enums.UserRole

@Entity
data class TeamMember(
    @Id @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, name = "USERNAME", unique = true)
    val name: String,

    @Column(nullable = false, name = "PASSWORD")
    val password: String,

    @Column(nullable = false, name = "ROLE")
    @Enumerated(STRING)
    val role: UserRole = UserRole.USER
)
