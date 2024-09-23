package models

import values.DateRange
import values.Money

data class Rent(
    val game: Game,
    val user: User,
    val rentDuration: DateRange
) {
    companion object {
        fun from(game: Game, user: User, rentDuration: DateRange): Result<Rent> {
            return runCatching { Rent(game, user, rentDuration) }
        }
    }

    val price: Money
        get() {
            return Money((game.price.cents * rentDuration.durationInDays() * user.planTier.priceMultiplier).toLong())
        }

    override fun toString(): String {
        return "Rent of ${game.title} for the value of $price"
    }

}