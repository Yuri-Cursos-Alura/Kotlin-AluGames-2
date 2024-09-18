package com.yuri_kotlin_learning.values

import java.time.LocalDate

@JvmInline
value class Birthday(val value: LocalDate) {
    init {
        if (value.isAfter(LocalDate.now())) throw BirthdayValidationException.FutureBirthdayException("Birthday must be in the past")
        if (value.plusYears(13).isAfter(LocalDate.now())) throw BirthdayValidationException.NotOldEnoughException("User must be at least 13 years old")
    }

    companion object {
        fun from(value: LocalDate): Result<Birthday> {
            return runCatching { Birthday(value) }
        }
    }
}

sealed class BirthdayValidationException(message: String) : Exception(message) {
    class FutureBirthdayException(message: String) : BirthdayValidationException(message)
    class NotOldEnoughException(message: String) : BirthdayValidationException(message)
}