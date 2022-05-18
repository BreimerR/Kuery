package libetal.libraries.kuery.core.statements

import libetal.libraries.kuery.core.entities.Entity

class Delete<T, E : Entity<T>>(override var sql: String) : Statement<T, E>() {
    override lateinit var entity: E
}

// TODO STORE COLUMNS INSIDE THE DATABASE OBJECT THUS MEANS THAT THE COLUMNS WILL NEED TO KNOW WHICH
// DATABASE THEY ARE UTILIZING