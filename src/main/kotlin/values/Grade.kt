package values

@JvmInline
value class Grade(val value: Int) {
    init {
        if (value !in 1..10) throw GradeValidationException.OutOfBoundException("Grade must be between 1 and 10")
    }

    companion object {
        fun from(value: Int): Result<Grade> {
            return runCatching { Grade(value.coerceIn(1, 10)) }
        }
    }
}

sealed class GradeValidationException {
    class OutOfBoundException(message: String) : Exception(message)
}
