package com.yuri_kotlin_learning.services

import com.yuri_kotlin_learning.models.User
import com.yuri_kotlin_learning.values.Birthday
import com.yuri_kotlin_learning.values.Email
import com.yuri_kotlin_learning.values.Username
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class SharkApi(
    private val _client: HttpClient = HttpClient.newHttpClient(),
    private val _json: Json = Json {
        ignoreUnknownKeys = true
    }
) {

    private companion object {
        const val BASE_URI = "https://www.cheapshark.com/api/1.0/games"
    }

    private fun toGame(str: String): ApiGame? {
        return runCatching { _json.decodeFromString<ApiGame>(str) }.getOrNull()
    }

    private fun toApiGamer(str: String): List<ApiGamer>? {
        return runCatching { _json.decodeFromString<List<ApiGamer>>(str) }.getOrNull()
    }

    fun getGameById(id: Int): ApiGame? {
        val uri = URI.create("$BASE_URI?id=$id")
        val request = HttpRequest.newBuilder()
            .uri(uri)
            .build()

        val response = _client.send(request, HttpResponse.BodyHandlers.ofString())

        val text: String = response?.body() ?: return null

        return toGame(text)
    }

    fun getGamers(): List<ApiGamer>? {
        val uri = URI.create("https://raw.githubusercontent.com/jeniblodev/arquivosJson/main/gamers.json")
        val request = HttpRequest.newBuilder()
            .uri(uri)
            .build()

        val response = _client.send(request, HttpResponse.BodyHandlers.ofString())

        val text: String = response?.body() ?: return null

        return toApiGamer(text)
    }
}

@Serializable
data class ApiGame(val info: ApiInfo)

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