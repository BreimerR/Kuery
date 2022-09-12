package libetal.libraries.kuery.sqlite.exceptions

import libetal.libraries.kuery.sqlite.exceptions.KSQLiteException

class KSQLiteOutOfBoundColumn : KSQLiteException {
    constructor(code: Int) : super(code)
    constructor(message: String) : super(message)
    constructor(index: Int, count: Int) : this("Provided index $index while columns count is $count")
}