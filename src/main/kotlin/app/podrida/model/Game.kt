package app.podrida.model

import app.podrida.utils.PlayerScoresConverter
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "games")
class Game(
    @Id
    val id: UUID = UUID.randomUUID(),

    val name: String = "",

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "created_by")
    val createdBy: User?,

    @Convert(converter = PlayerScoresConverter::class)
    @Column(name = "player_scores", columnDefinition = "TEXT")
    val playerScores: Map<String, Int> = emptyMap()
) {
    constructor() : this(
        name = "",
        createdBy = null
    )
}
