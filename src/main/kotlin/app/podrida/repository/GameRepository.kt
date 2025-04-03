package app.podrida.repository

import app.podrida.model.Game
import app.podrida.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface GameRepository : JpaRepository<Game, UUID> {
    fun findByCreatedByOrderByCreatedAtDesc(user: User): List<Game>
}
