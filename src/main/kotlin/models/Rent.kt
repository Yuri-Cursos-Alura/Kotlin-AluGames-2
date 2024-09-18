package com.yuri_kotlin_learning.models

data class Rent(
    val game: Game, val user: User
) {
    override fun toString(): String {
        return "Rent of ${game.title} for ${user.name})"
    }
}