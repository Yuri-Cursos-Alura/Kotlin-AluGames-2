import models.Rent
import services.SharkApi
import values.DateRange
import java.time.LocalDate

fun main() {
    val sharkApi = SharkApi()

    val gamers = sharkApi.getGamers()
        .orEmpty()
        .map { it.toUser().getOrThrow() }

    val games = sharkApi.getGames()
        .orEmpty()
        .map { it.toGame() }

    val myGamer = gamers.getOrNull(3) ?: throw IllegalStateException("Gamer not found")
    val myGame = games.lastOrNull() ?: throw IllegalStateException("List was empty")

    val range = DateRange.from(LocalDate.now(), LocalDate.now().plusDays(7)).getOrThrow()

    println("Days: ${range.durationInDays()}")

    val rent = Rent(myGame, range)

    myGamer.rent(rent)
    val anotherGame = games.getOrNull(1) ?: throw IllegalStateException("Game not found")
    myGamer.rent(Rent(anotherGame, DateRange(LocalDate.now(), LocalDate.now().plusDays(3))))

    myGamer.rentedGames.forEach(::println)
}