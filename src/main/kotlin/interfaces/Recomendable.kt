package interfaces

import values.Grade

interface Recomendable {
    val average: Double
    fun recomend(grade: Grade)
}