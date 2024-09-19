package com.yuri_kotlin_learning

import com.yuri_kotlin_learning.models.Rent
import com.yuri_kotlin_learning.services.SharkApi
import com.yuri_kotlin_learning.values.DateRange

fun main() {
    val sharkApi = SharkApi()

    val gamers = sharkApi.getGamers()
        .orEmpty()
        .map { it.toUser().getOrThrow() }

    val games = sharkApi.getGames()
        .orEmpty()
        .map { it.toGame() }

    val myGamer = gamers.getOrNull(3) ?: throw IllegalStateException("Gamer not found")
    val myGame = games.lastOrNull() ?: throw IllegalStateException("List was empty")

    val range = DateRange.from("19/09/2024", "23/09/2024").getOrThrow()

    val rent = Rent(myGame, myGamer, range)

    println(rent)
}