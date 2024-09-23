package services

import dtos.ApiGame
import dtos.ApiGamer
import dtos.GitApiGame
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

    companion object {
        private const val BASE_URI = "https://www.cheapshark.com/api/1.0/games"
        private const val URI_GAMES = "https://raw.githubusercontent.com/jeniblodev/arquivosJson/main/jogos.json"
        private const val URI_GAMERS = "https://raw.githubusercontent.com/jeniblodev/arquivosJson/main/gamers.json"
    }

    private fun toApiGame(str: String): ApiGame? {
        return runCatching { _json.decodeFromString<ApiGame>(str) }.getOrNull()
    }

    private fun toGitApiGameList(str: String): List<GitApiGame>? {
        return runCatching { _json.decodeFromString<List<GitApiGame>>(str) }.getOrNull()
    }

    private fun toApiGamer(str: String): List<ApiGamer>? {
        return runCatching { _json.decodeFromString<List<ApiGamer>>(str) }.getOrNull()
    }

    private fun String.toGetRequest(): HttpRequest {
        val uri = URI.create(this)
        return HttpRequest.newBuilder()
            .uri(uri)
            .build()
    }

    fun getGameById(id: Int): ApiGame? {
        val request = "$BASE_URI?id=$id".toGetRequest()

        val response = _client.send(request, HttpResponse.BodyHandlers.ofString())

        val text: String = response?.body() ?: return null

        return toApiGame(text)
    }

    fun getGames(): List<GitApiGame>? {
        val response = _client.send(URI_GAMES.toGetRequest(), HttpResponse.BodyHandlers.ofString())

        val text: String = response?.body() ?: return emptyList()

        return toGitApiGameList(text)
    }

    fun getGamers(): List<ApiGamer>? {
        val request = URI_GAMERS
            .toGetRequest()

        val response = _client.send(request, HttpResponse.BodyHandlers.ofString())

        val text: String = response?.body() ?: return null

        return toApiGamer(text)
    }
}

