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
            return Result.success(Rent(game, user, rentDuration))
        }
    }

    val price: Money
        get() {
            user.rentedGames
                .sortedBy { it.rentDuration.start }
                .indexOf(this)
                .let { if ((it + 1) <= user.planTier.gameQuantity) return Money(0) }

            val reputationDiscount: Double = if (user.average > 8) user.planTier.reputationMultiplier else 1.0

            return Money((
                    game.price.cents *
                    rentDuration.durationInDays() *
                    user.planTier.basePriceMultiplier *
                    reputationDiscount
                    ).toLong())
        }

    override fun toString(): String {
        return "Rent of ${game.title} for the value of $price"
    }

}