package com.yuri_kotlin_learning.models

import com.yuri_kotlin_learning.values.Money

data class Game(
    val title: String,
    val thumb: String,
    val description: String,
    val price: Money
) {

    override fun toString(): String {
        return "Game(title='$title', thumb='$thumb', description='$description', price='$price')"
    }
}