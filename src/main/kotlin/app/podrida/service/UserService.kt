package app.podrida.service

import app.podrida.model.User
import app.podrida.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository) {

    @Transactional
    fun createUser(auth0Id: String, email: String): User {
        // Check if user already exists to avoid creating duplicates
        val existingUser = userRepository.findByAuth0Id(auth0Id)
        if (existingUser != null) {
            return existingUser
        }

        // Create new user without specifying an ID (let JPA generate it)
        val user = User(auth0Id = auth0Id, email = email)
        return userRepository.save(user)
    }


    fun findByAuth0Id(id: String): User? = userRepository.findByAuth0Id(id)
}