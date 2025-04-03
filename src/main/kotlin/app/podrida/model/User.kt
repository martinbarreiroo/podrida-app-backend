package app.podrida.model

import jakarta.persistence.*
import java.util.*


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
    var version: Long = 0
)

 {
    // Required no-arg constructor for JPA
    protected constructor() : this(auth0Id = "", email = "")
}
