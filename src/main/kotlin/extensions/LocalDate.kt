package extensions

import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val ddMMyyyyFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")

object LocalDateExt {
    fun ddMMyyyy(value: String): LocalDate? {
        return runCatching {
            LocalDate.parse(value, ddMMyyyyFormat)
        }.getOrNull()
    }
}