package app.podrida.dto

import jakarta.validation.constraints.NotEmpty

data class GameCreateRequest(
    var name: String = "",
    @field:NotEmpty(message = "Player scores cannot be empty")
    val playerScores: Map<String, Int>,
)
