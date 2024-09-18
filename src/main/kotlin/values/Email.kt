package com.yuri_kotlin_learning.values

@JvmInline
value class Email(val value: String) {
    init {
        if ("@" !in value) throw EmailValidationException.NoAtSignException("Email must contain '@'")
        if ("." !in value) throw EmailValidationException.NoDomainException("Email must contain a domain")

        val domain = value.split(".").lastOrNull() ?: throw EmailValidationException.NoDomainException("Email must contain a domain")

        if (domain.length < 3) throw EmailValidationException.SmallDomainException("Domain must have at least 3 characters")
    }

    companion object {
        fun from(value: String): Result<Email> {
            return runCatching { Email(value) }
        }
    }

}

sealed class EmailValidationException(message: String) : Exception(message) {
    class NoDomainException(message: String) : EmailValidationException(message);
    class NoAtSignException(message: String) : EmailValidationException(message);
    class SmallDomainException(message: String) : EmailValidationException(message);
}
