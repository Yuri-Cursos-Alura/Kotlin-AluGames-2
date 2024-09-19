package com.yuri_kotlin_learning.values

import com.yuri_kotlin_learning.extensions.LocalDateExt
import java.time.LocalDate
import java.time.Period

data class DateRange(
    val start: LocalDate,
    val end: LocalDate
) {
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

    init {
        if (end.isBefore(start))
            throw DateRangeValidationException.EndBeforeStartException("End date must be after start date")
    }

    fun contains(date: LocalDate): Boolean {
        return date in start..end
    }

    fun durationInDays(): Int {
        return Period.between(start, end).days
    }

    override fun toString(): String {
        return "$start - $end"
    }
}

sealed class DateRangeValidationException(message: String) : Exception(message) {
    class EndBeforeStartException(message: String) : DateRangeValidationException(message)
}