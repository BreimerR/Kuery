package libetal.libraries.kuery.sqlite.exceptions

import kotlinx.cinterop.*
import libetal.libraries.kuery.sqlite.exceptions.KSQLiteException

class KSQLitePrepareStatementException : KSQLiteException {
    constructor(code: Int) : super(code)
    constructor(message: String) : super(message)
    constructor(code: Int, description: String) : this("Code: $code. Message: $description")
    constructor(error: CPointerVarOf<CPointer<ByteVarOf<Byte>>>) : this(error.value?.toKString() ?: "No Error")
}