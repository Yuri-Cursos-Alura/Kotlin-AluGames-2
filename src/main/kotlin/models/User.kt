package models

import values.Birthday
import values.Email
import values.Username
import java.util.*

data class User(
    var name: Username,
    var email: Email,
    var dateOfBirth: Birthday,
    var nickname: String,
    var planTier: PlanTier,
    private val _rentedGames: MutableList<Rent> = mutableListOf()
) {
    val id: UUID = UUID.randomUUID()

    fun rent(rent: Rent) = _rentedGames.add(rent)

    val rentedGames: List<Rent>
            get() = _rentedGames.toList()
}

enum class PlanTier {
    NONE,
    STANDARD,
    PREMIUM;

    val priceMultiplier: Double
        get() = when (this) {
            NONE -> 1.0
            STANDARD -> 0.9
            PREMIUM -> 0.8
        }
}