package libetal.libraries.kuery.mariadb.exceptions

class MariaDbException(private val code: UInt, message: String) : RuntimeException("Code: $code\n $message") {
    val type
        get() = MariaDbExceptions(code)
}

enum class MariaDbExceptions(val code: UInt) {
    TABLE_EXISTS(1050u),
    UNEXPECTED_NULL(1364u),
    INCORRECT_DATE(1292u),
    UNKNOWN_ENTITY(1051u);

    companion object {
        operator fun invoke(code: UInt) = when (code) {
            TABLE_EXISTS.code -> TABLE_EXISTS
            UNKNOWN_ENTITY.code -> UNKNOWN_ENTITY
            UNEXPECTED_NULL.code -> UNEXPECTED_NULL
            INCORRECT_DATE.code -> INCORRECT_DATE
            else -> throw RuntimeException("Unsupported error code")
        }
    }
}