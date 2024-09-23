package models

import values.Money
import java.util.UUID

data class Game(
    val title: String,
    val thumb: String,
    val description: String,
    val price: Money
) {
    val id: UUID = UUID.randomUUID()

    override fun toString(): String {
        return "Game(title='$title', thumb='$thumb', description='$description', price='$price')"
    }
}