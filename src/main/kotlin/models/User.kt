package models

import values.Birthday
import values.Email
import values.Money
import values.Username
import java.util.*

data class User(
    val name: Username,
    val email: Email,
    val dateOfBirth: Birthday,
    val nickname: String,
    val planTier: PlanTier,
    private val _rentedGames: MutableList<Rent> = mutableListOf()
) {
    val id: UUID = UUID.randomUUID()

    fun rent(rent: Rent) = _rentedGames.add(rent)

    val rentedGames: List<Rent>
            get() = _rentedGames.toList()

    val totalMonthPrice: Money
        get() = Money(_rentedGames.sumOf { it.price.cents })
}

enum class PlanTier(val price: Money, val gameQuantity: Int, val priceMultiplier: Double) {
    NONE(price = Money(0), gameQuantity = 1, priceMultiplier = 1.0),
    STANDARD(price = Money(10.0), gameQuantity = 2, priceMultiplier = 0.9),
    PREMIUM(price = Money(15.0), gameQuantity = 5, priceMultiplier = 0.8);
}