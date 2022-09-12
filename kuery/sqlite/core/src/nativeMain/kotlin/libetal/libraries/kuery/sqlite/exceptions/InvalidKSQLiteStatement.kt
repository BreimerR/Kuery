package libetal.libraries.kuery.sqlite.exceptions

class InvalidKSQLiteStatement : KSQLiteException {
    constructor(code: Int) : super(code)
    constructor(message: String) : super(message)
}


