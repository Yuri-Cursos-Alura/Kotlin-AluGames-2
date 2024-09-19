package com.yuri_kotlin_learning.values

import com.yuri_kotlin_learning.extensions.LocalDateExt
import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class DateRange(
    val start: LocalDate,
    val end: LocalDate
) {
    init {
        if (end.isBefore(start))
            throw DateRangeValidationException.EndBeforeStartException("End date must be after start date")

        if (start.daysSinceEpoch() == end.daysSinceEpoch())
            throw DateRangeValidationException.StartAndEndEqualException("Start and end date must be different")
    }

    companion object {
        fun from(start: LocalDate, end: LocalDate): Result<DateRange> {
            return runCatching { DateRange(start, end) }
        }

        fun from (start: String, end: String): Result<DateRange> {
            return runCatching {
                DateRange(LocalDateExt.ddMMyyyy(start) ?: throw IllegalArgumentException("Invalid date format: $start"),
                    LocalDateExt.ddMMyyyy(end) ?: throw IllegalArgumentException("Invalid date format: $end"))
            }
        }
    }

    fun contains(date: LocalDate): Boolean {
        return date in start..end
    }

    fun durationInDays(): Long {
        return ChronoUnit.DAYS.between(start, end)
    }

    fun overlaps(other: DateRange): Boolean {
        return this.start <= other.end && other.start <= this.end
    }

    override fun toString(): String {
        return "$start - $end"
    }

    private fun LocalDate.daysSinceEpoch(): Long {
        return ChronoUnit.DAYS.between(LocalDate.ofEpochDay(0), this)
    }
}

sealed class DateRangeValidationException(message: String) : Exception(message) {
    class EndBeforeStartException(message: String) : DateRangeValidationException(message)
    class StartAndEndEqualException(message: String) : DateRangeValidationException(message)
}