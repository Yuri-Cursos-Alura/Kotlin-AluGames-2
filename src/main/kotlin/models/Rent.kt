package models

import values.DateRange
import values.Money

data class Rent(
    val game: Game,
    val rentDuration: DateRange
) {
    companion object {
        fun from(game: Game, user: User, rentDuration: DateRange): Result<Rent> {
            return runCatching { Rent(game, rentDuration) }
        }
    }

    val price: Money
        get() = Money(game.price.value * rentDuration.durationInDays())

    override fun toString(): String {
        return "Rent of ${game.title} for the value of $price"
    }

}