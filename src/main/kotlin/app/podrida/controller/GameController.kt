package app.podrida.controller

import app.podrida.dto.GameCreateRequest
import app.podrida.dto.GameResponse
import app.podrida.service.GameService
import app.podrida.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/games")
class GameController(
    private val gameService: GameService,
    private val userService: UserService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createGame(
        @RequestBody request: GameCreateRequest,
        @AuthenticationPrincipal principal: OAuth2User
    ): GameResponse {
        val user = userService.findOrCreateUser(
            auth0Id = principal.name,
            email = principal.attributes["email"] as String,
            name = principal.attributes["name"] as String
        )

        return gameService.createGame(request, user)
    }

    @GetMapping
    fun getUserGames(@AuthenticationPrincipal principal: OAuth2User): List<GameResponse> {
        val user = userService.findByAuth0Id(principal.name)
            ?: throw IllegalStateException("User not found")

        return gameService.getUserGames(user)
    }
}