package libetal.libraries.kuery.sqlite.example.database

import libetal.libraries.kuery.KSQLite
import libetal.libraries.kuery.annotations.databases.SQLite

@SQLite("kuery")
object Database : KSQLite()