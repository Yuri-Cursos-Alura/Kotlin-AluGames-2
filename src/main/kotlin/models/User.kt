package com.yuri_kotlin_learning.models

import com.yuri_kotlin_learning.values.Birthday
import com.yuri_kotlin_learning.values.Email
import com.yuri_kotlin_learning.values.Username
import java.util.*

data class User(
    var name: Username,
    var email: Email,
    var dateOfBirth: Birthday,
    var games: MutableList<Game> = mutableListOf()
) {
    val id: UUID = UUID.randomUUID()
}