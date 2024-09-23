package values

import java.time.LocalDate
import java.time.format.DateTimeFormatter

@JvmInline
value class Birthday(val value: LocalDate) {
    init {
        if (value.isAfter(LocalDate.now())) throw BirthdayValidationException.FutureBirthdayException("Birthday must be in the past")
        if (value.plusYears(13).isAfter(LocalDate.now())) throw BirthdayValidationException.NotOldEnoughException("User must be at least 13 years old")
    }

    companion object {
        val standardFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        fun from(value: LocalDate): Result<Birthday> {
            return runCatching { Birthday(value) }
        }

        fun from(value: String, format: DateTimeFormatter? = null): Result<Birthday> {
            return runCatching { Birthday(LocalDate.parse(value, format ?: standardFormat)) }
        }
    }

    override fun toString(): String {
        return value.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    }
}

sealed class BirthdayValidationException(message: String) : Exception(message) {
    class FutureBirthdayException(message: String) : BirthdayValidationException(message)
    class NotOldEnoughException(message: String) : BirthdayValidationException(message)
}