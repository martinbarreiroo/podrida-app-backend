package app.podrida.controller

import app.podrida.service.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(private val userService: UserService) {

    @GetMapping("/user")
    fun getUser(@AuthenticationPrincipal jwt: Jwt): Map<String, Any?> {
        // Extract user info from the JWT
        val auth0Id = jwt.subject
        val email = jwt.claims["username"] as? String ?: ""

        // Check if the user already exists in the database
        val existingUser = userService.findByAuth0Id(auth0Id)
        if (existingUser != null) {
            return mapOf(
                "id" to existingUser.auth0Id,
                "email" to existingUser.email,
            )
        }

        val user = userService.createUser(
            auth0Id = auth0Id,
            email = email
        )

        return mapOf(
            "id" to user.auth0Id,
            "email" to user.email,
        )
    }

    @GetMapping("/success")
    fun authSuccess(): Map<String, String> {
        return mapOf("status" to "success")
    }
}