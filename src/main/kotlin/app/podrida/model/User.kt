package app.podrida.model

import jakarta.persistence.*
import java.util.*


@Entity
    @Table(name = "users")
    class User(
    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: String?,

    @Column(nullable = false, unique = true)
        val email: String,

    val name: String,


    @Column(name = "auth0_id", unique = true)
        val auth0Id: String
    ) {
        constructor() : this(
            id = null,
            email = "",
            name = "",
            auth0Id = ""
        )
    }