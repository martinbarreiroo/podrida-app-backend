package app.podrida.service

import app.podrida.model.User
import app.podrida.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun findOrCreateUser(auth0Id: String, email: String, name: String): User {
        return userRepository.findByAuth0Id(auth0Id) ?: User(
            email = email,
            name = name,
            auth0Id = auth0Id
        ).also { userRepository.save(it) }
    }

    fun findByAuth0Id(id: String): User? = userRepository.findById(id).orElse(null)
}