package libetal.libraries.kuery.core.statements.builders

import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.name
import libetal.libraries.kuery.core.statements.Delete
import libetal.libraries.kuery.core.statements.Select

class SelectStatementBuilder<T, E : Entity<T>>(entity: E, vararg columns: Column<*>) :
    EntityStatementBuilder<T, E, Select<T, E>>("SELECT ${columns.toSql(entity)}", entity = entity, columns = columns) , WhereStatementBuilder<T, E, Select<T, E>>{

    override fun build(extras: String): Select<T, E> = Select("${this.sql} $extras", entity).also { selectQuery ->
        selectQuery.columns.addAll(columns)
    }

    override fun  E.buildWhere(where: String, boundWheres: String): Select<T, E> {
        TODO("Not yet implemented")
    }

}





