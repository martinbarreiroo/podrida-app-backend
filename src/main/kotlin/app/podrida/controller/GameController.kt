package app.podrida.controller

import app.podrida.dto.GameCreateRequest
import app.podrida.dto.GameResponse
import app.podrida.model.User
import app.podrida.service.GameService
import app.podrida.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
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
        @AuthenticationPrincipal jwt: Jwt
    ): GameResponse {
        val user: User
        val auth0Id = jwt.subject
        val email = jwt.claims["email"] as? String ?: ""

        // Check if the user already exists in the database
        user = userService.findByAuth0Id(auth0Id) ?: userService.createUser(
            auth0Id = auth0Id,
            email = email
        )


        return gameService.createGame(request, user)
    }

    @GetMapping
    fun getUserGames(@AuthenticationPrincipal jwt: Jwt): List<GameResponse> {
        val auth0Id = jwt.subject
        val user = userService.findByAuth0Id(auth0Id)
            ?: throw IllegalStateException("User not found")

        return gameService.getUserGames(user)
    }
}