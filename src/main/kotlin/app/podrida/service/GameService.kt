package app.podrida.service

import app.podrida.dto.GameCreateRequest
import app.podrida.dto.GameResponse
import app.podrida.dto.UserMinimalDto
import app.podrida.model.Game
import app.podrida.model.User
import app.podrida.repository.GameRepository
import org.springframework.stereotype.Service

@Service
class GameService(private val gameRepository: GameRepository) {

    fun createGame(request: GameCreateRequest, user: User): GameResponse {
        val game = Game(
            name = request.name,
            createdBy = user,
            playerScores = request.playerScores
        )

        val savedGame = gameRepository.save(game)
        var userId = Long.MIN_VALUE

        if (user.id != null) {
            userId = user.id
        }

        return GameResponse(
            id = savedGame.id.toString(),
            name = savedGame.name,
            createdAt = savedGame.createdAt,
            playerScores = savedGame.playerScores,
            createdBy = UserMinimalDto(userId, user.name)
        )
    }

    fun getUserGames(user: User): List<GameResponse> {

        var userId = Long.MIN_VALUE
        if (user.id != null) {
            userId = user.id
        }

        return gameRepository.findByCreatedByOrderByCreatedAtDesc(user).map { game ->
            GameResponse(
                id = game.id.toString(),
                name = game.name,
                createdAt = game.createdAt,
                playerScores = game.playerScores,
                createdBy = UserMinimalDto(userId, user.name)
            )
        }
    }
}