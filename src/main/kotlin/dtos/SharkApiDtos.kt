package com.yuri_kotlin_learning.dtos

import com.yuri_kotlin_learning.models.Game
import com.yuri_kotlin_learning.models.User
import com.yuri_kotlin_learning.values.Birthday
import com.yuri_kotlin_learning.values.Email
import com.yuri_kotlin_learning.values.Money
import com.yuri_kotlin_learning.values.Username
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiGame(val info: ApiInfo) {
    fun toGame(description: String): Game {
        return Game(this.info.title, this.info.thumb, description, Money(0))
    }
}

@Serializable
data class ApiInfo(val title: String, val thumb: String)

@Serializable
data class ApiGamer(
    @SerialName("nome")
    val name: String,
    @SerialName("email")
    val email: String,
    @SerialName("dataNascimento")
    val dateOfBirth: String,
    @SerialName("usuario")
    val nickname: String,
    @SerialName("idInterno")
    val internalId: String
) {
    fun toUser(): Result<User> {
        val username = Username.from(this.name)
            .getOrElse { return Result.failure(IllegalStateException(it.message)) }
        val email = Email.from(this.email)
            .getOrElse { return Result.failure(IllegalStateException(it.message)) }
        val birthday = Birthday.from(dateOfBirth)
            .getOrElse { return Result.failure(IllegalStateException(it.message)) }

        return User(username, email, birthday, this.nickname).let { Result.success(it) }
    }
}

@Serializable
data class GitApiGame(
    @SerialName("titulo")
    val title: String,
    @SerialName("capa")
    val thumb: String,
    @SerialName("preco")
    val preco: Float,
    @SerialName("descricao")
    val description: String
) {
    fun toGame(): Game {
        val money = Money.from(preco)
            .getOrElse { throw IllegalStateException(it.message) }

        return Game(this.title, this.thumb, this.description, price = money)
    }
}