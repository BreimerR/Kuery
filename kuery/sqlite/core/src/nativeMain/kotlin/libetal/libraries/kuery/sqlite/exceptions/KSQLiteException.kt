package libetal.libraries.kuery.sqlite.exceptions

import kotlinx.cinterop.toKString
import libetal.interop.sqlite3.sqlite3_errstr

abstract class KSQLiteException(message: String) : RuntimeException(message) {
    constructor(code: Int) : this("${sqlite3_errstr(code)?.toKString()}")
}