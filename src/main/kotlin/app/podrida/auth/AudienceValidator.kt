package app.podrida.auth

import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult
import org.springframework.security.oauth2.jwt.Jwt

class AudienceValidator(private val audience: String) : OAuth2TokenValidator<Jwt> {
    override fun validate(jwt: Jwt): OAuth2TokenValidatorResult {
        val audiences = jwt.audience
        if (audiences.contains(audience)) {
            return OAuth2TokenValidatorResult.success()
        }

        return OAuth2TokenValidatorResult.failure(
            OAuth2Error("invalid_token", "The required audience is missing", null),
        )
    }
}
