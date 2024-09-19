package com.yuri_kotlin_learning.models

import com.yuri_kotlin_learning.values.DateRange
import com.yuri_kotlin_learning.values.Money

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
        get() = Money(game.price.value * rentDuration.durationInDays())

    override fun toString(): String {
        return "Rent of ${game.title} for ${user.name} with the value of $price"
    }

}