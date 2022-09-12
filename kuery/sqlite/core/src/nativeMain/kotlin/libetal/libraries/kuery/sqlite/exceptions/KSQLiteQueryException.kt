package libetal.libraries.kuery.sqlite.exceptions

import kotlinx.cinterop.*

class KSQLiteQueryException : KSQLiteException {
    constructor(code: Int) : super(code)
    constructor(message: String) : super(message)
    constructor(code: Int, description: String) : this("Code: $code. Message: $description")
    constructor(error: CPointerVarOf<CPointer<ByteVarOf<Byte>>>) : this(error.value?.toKString() ?: "No Error")
}



fun defaultErrorHandler(code: Int, description: String) {
    throw KSQLiteQueryException(
        code,
        description
    )
}
