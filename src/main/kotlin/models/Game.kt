package com.yuri_kotlin_learning.models

data class Game(val title: String, val thumb: String, val description: String) {

    override fun toString(): String {
        return "Game(title='$title', thumb='$thumb', description='$description')"
    }
}