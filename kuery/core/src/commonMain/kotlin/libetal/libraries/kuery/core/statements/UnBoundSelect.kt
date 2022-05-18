package libetal.libraries.kuery.core.statements

import libetal.libraries.kuery.core.entities.Entity

class UnBoundSelect<T, E : Entity<T>>(sql: String,entity:E) : Select<T, E>(sql,entity), UnBoundStatement {
    override val arguments: MutableList<String> by lazy {
        mutableListOf()
    }
}
