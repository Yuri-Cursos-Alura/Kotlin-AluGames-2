package com.yuri_kotlin_learning

import com.yuri_kotlin_learning.services.SharkApi

fun main() {
    val sharkApi = SharkApi()

    val gamers = sharkApi.getGamers()
        .orEmpty()
        .map { it.toUser().getOrThrow() }

    println(gamers)
}