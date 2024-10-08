import models.PlanTier
import models.Rent
import services.SharkApi
import values.DateRange
import values.Grade
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

    val rent = Rent(myGame, myGamer, range)

    myGamer.rent(rent)
    val anotherGame = games.getOrNull(1) ?: throw IllegalStateException("Game not found")
    myGamer.rent(Rent(anotherGame, myGamer, DateRange(LocalDate.now(), LocalDate.now().plusDays(3))))

    myGamer.rentedGames.forEach(::println)
    println("Total monthly price: ${myGamer.totalMonthPrice}")

    myGamer.recomend(Grade.from(10).getOrThrow())
    myGamer.recomend(Grade.from(9).getOrThrow())
    myGamer.recomend(Grade.from(8).getOrThrow())
    println("Average grade: ${myGamer.average}")
    println("Total monthly price with good reputation: ${myGamer.totalMonthPrice}")
}