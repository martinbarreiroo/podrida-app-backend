package app.podrida.dto

import java.time.LocalDateTime

data class GameResponse(
    val id: String,
    val name: String,
    val createdAt: LocalDateTime,
    val createdBy: UserMinimalDto,
    val playerScores: Map<String, Int>
)