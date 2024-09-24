package models

import interfaces.Recomendable
import values.*
import java.util.*

data class User(
    val name: Username,
    val email: Email,
    val dateOfBirth: Birthday,
    val nickname: String,
    val planTier: PlanTier
) : Recomendable {
    private val _rentedGames: MutableList<Rent> = mutableListOf()
    private val _grades: MutableList<Int> = mutableListOf()
    private val _gameRecommendations: MutableList<Pair<Game, Int>> = mutableListOf()
    val id: UUID = UUID.randomUUID()


    fun rent(rent: Rent) = _rentedGames.add(rent)

    val rentedGames: List<Rent>
            get() = _rentedGames.toList()

    val totalMonthPrice: Money
        get() = Money(_rentedGames.sumOf { it.price.cents })

    override val average: Double
        get() = _grades.average()

    override fun recomend(grade: Grade) {
        _grades.add(grade.value)
    }

    enum class RecommendGameResult { SUCCESS, ALREADY_RECOMMENDED }
    fun recommendGame(game: Game, grade: Grade): RecommendGameResult {
        if (_gameRecommendations.any { it.first == game }) return RecommendGameResult.ALREADY_RECOMMENDED

        _gameRecommendations.add(game to grade.value)
        return RecommendGameResult.SUCCESS
    }
}

enum class PlanTier(
    val price: Money,
    val gameQuantity: Int,
    val basePriceMultiplier: Double,
    val reputationMultiplier: Double) {
    NONE(price = Money(0), gameQuantity = 0, basePriceMultiplier = 1.0, reputationMultiplier = 0.9),
    STANDARD(price = Money(10.0), gameQuantity = 2, basePriceMultiplier = 0.9, reputationMultiplier = 0.75),
    PREMIUM(price = Money(15.0), gameQuantity = 5, basePriceMultiplier = 0.8, reputationMultiplier = 0.5);
}