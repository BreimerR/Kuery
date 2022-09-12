package libetal.libraries.kuery.sqlite.exceptions

class InvalidColumnIndexException(index: Int) : KSQLiteException("Column: $index")

