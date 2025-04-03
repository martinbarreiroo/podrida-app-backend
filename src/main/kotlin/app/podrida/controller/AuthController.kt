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
        val email = jwt.claims["email"] as? String ?: ""
        val name = jwt.claims["name"] as? String ?: ""

        val user = userService.findOrCreateUser(
            auth0Id = auth0Id,
            email = email,
            name = name
        )

        return mapOf(
            "id" to user.id,
            "name" to user.name,
            "email" to user.email,
        )
    }

    @GetMapping("/success")
    fun authSuccess(): Map<String, String> {
        return mapOf("status" to "success")
    }
}