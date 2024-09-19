package com.yuri_kotlin_learning.values

@JvmInline
value class Money(val value: Long) {
    init {
        if (value < 0) throw PriceValidationException.NegativeValueException("Price value must be positive")
    }

    companion object {
        fun from(value: Long): Result<Money> {
            return runCatching { Money(value) }
        }

        fun from(value: Float): Result<Money> {
            return runCatching { Money((value * 100).toLong()) }
        }

        fun from(value: Int): Result<Money> {
            return runCatching { Money(value.toLong()) }
        }
    }

    override fun toString(): String {
        return "R$${"%.2f".format(this.value.toFloat() / 100)}"
    }

    fun toFloat(): Float {
        return value.toFloat() / 100
    }
}

sealed class PriceValidationException(message: String) : Exception(message) {
    class NegativeValueException(message: String) : PriceValidationException(message)
}