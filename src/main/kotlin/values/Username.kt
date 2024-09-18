package com.yuri_kotlin_learning.values

@JvmInline
value class Username(val value: String) {
    init {
        if (value.isBlank()) throw UsernameValidationException.BlankException("Username must not be blank")
        if (value.length < 3) throw UsernameValidationException.SmallException("Username must have at least 3 characters")
    }

    companion object {
        fun from(value: String): Result<Username> {
            return runCatching { Username(value) }
        }
    }
}

sealed class UsernameValidationException(message: String) : Exception(message) {
    class SmallException(message: String) : UsernameValidationException(message)
    class BlankException(message: String) : UsernameValidationException(message)
}