package app.podrida.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Version
import java.util.UUID

@Entity
@Table(name = "users")
open class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    @Column(unique = true)
    val auth0Id: String,
    val email: String,
    @Version
    var version: Long = 0,
) {
    // Required no-arg constructor for JPA
    protected constructor() : this(auth0Id = "", email = "")
}
