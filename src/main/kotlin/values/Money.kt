package values

@JvmInline
value class Money(val value: Long) {
    init {
        if (value < 0) throw PriceValidationException.NegativeValueException("Price value must be positive")
    }

    companion object {
        fun from(value: Long): Result<Money> = runCatching { Money(value) }

        fun from(value: Float): Result<Money> = runCatching { Money((value * 100).toLong()) }

        fun from(value: Double): Result<Money> = runCatching { Money((value * 100).toLong()) }

        fun from(value: Int): Result<Money> = runCatching { Money(value.toLong()) }
    }

    constructor(value: Int) : this(value.toLong())
    constructor(value: Float) : this((value * 100).toLong())
    constructor(value: Double) : this((value * 100).toLong())

    val cents: Long
        get() = value

    val dollars: Float
        get() = value.toFloat() / 100

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