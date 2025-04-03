package app.podrida.controller

import app.podrida.service.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(private val userService: UserService) {

    @GetMapping("/user")
    fun getUser(@AuthenticationPrincipal principal: OAuth2User): Map<String, Any?> {
        val user = userService.findOrCreateUser(
            auth0Id = principal.name,
            email = principal.attributes["email"] as String,
            name = principal.attributes["name"] as String
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