package com.yuri_kotlin_learning

import com.yuri_kotlin_learning.models.Rent
import com.yuri_kotlin_learning.services.SharkApi

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

    val rent = Rent(myGame, myGamer)

    println(rent)
}