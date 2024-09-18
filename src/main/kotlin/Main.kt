package com.yuri_kotlin_learning

import com.yuri_kotlin_learning.models.Game
import com.yuri_kotlin_learning.models.User
import com.yuri_kotlin_learning.services.SharkApi
import com.yuri_kotlin_learning.values.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object Util {
    val reading = Scanner(System.`in`)
}

fun main() {
    val user = createUser()

    println(user)

    do {
        val game = askUser()

        if (game != null) {
            user.games.add(game)
        }

        println("Deseja continuar? (s/n)")
    } while (Util.reading.nextLine().lowercase() == "s")

    user.games.sortBy { it.title }

    println("Todos os jogos favoritados: ")

    user.games.forEach {
        println("Nome: ${it.title}")
        println("Descrição: ${it.description}")
        println("-".repeat(30))
    }

}

fun createUser(): User {
    var name: Result<Username>
    var email: Result<Email>
    var birthday: Result<Birthday>

    do {
        println("Digite o nome do usuário: ")
        name = Username.from(Util.reading.nextLine())

        name.onFailure {
            when(it) {
                is UsernameValidationException.BlankException -> println("Nome não pode ser vazio.")
                is UsernameValidationException.SmallException -> println("Nome precisa ter pelo menos 3 caracteres.")
                else -> println("Nome inválido: ${it.message}")
            }
        }
    } while (name.isFailure)

    do {
        println("Digite o email do usuário: ")
        email = Email.from(Util.reading.nextLine())

        email.onFailure {
            println("Email inválido.")
        }
    } while (email.isFailure)

    do {
        println("Digite a data de nascimento do usuário (dd/MM/yyyy): ")
        val date = runCatching {
            LocalDate.parse(Util.reading.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        }

        date.onFailure { println("Data de nascimento não está no formato correto.") }
        if (date.isFailure) {
            birthday = Result.failure(Exception())
            continue
        }

        birthday = Birthday.from(date.getOrThrow())

        birthday.onFailure {
            when(it) {
                is BirthdayValidationException.FutureBirthdayException -> println("Data de nascimento não pode ser no futuro.")
                is BirthdayValidationException.NotOldEnoughException -> println("Usuário precisa ter pelo menos 13 anos.")
                else -> println("Data de nascimento inválida: ${it.message}")
            }
        }
    } while (birthday.isFailure)

    return User(name = name.getOrThrow(), email = email.getOrThrow(), dateOfBirth = birthday.getOrThrow())
}

fun askUser(): Game? {
    println("Digite o id do jogo: ")
    val id = Util.reading.nextLine()

    if (id == null) {
        println("Id inválido")
        return null
    }

    val intId = runCatching { id.toInt() }.getOrNull()

    if (intId == null) {
        println("Id precisa ser um número.")
        return null
    }

    val gameApi = SharkApi()

    val gameFromApi = gameApi.getGameById(intId)

    if (gameFromApi == null) {
        println("Jogo não encontrado.")
        return null
    }

    println(gameFromApi)

    println("Digite uma descrição: ")
    val description = Util.reading.nextLine()

    val game = Game.from(gameFromApi, description)

    return game
}
