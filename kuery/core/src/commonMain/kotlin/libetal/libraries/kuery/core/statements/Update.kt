package libetal.libraries.kuery.core.statements

import libetal.kotlin.expected
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.identifier

class Update<T, E : Entity<T>>(sql: String, entity: E) : Statement<T, E>(sql, entity), WhereStatement {

    override val wheres: MutableList<Pair<String, Any>> by laziest {
        mutableListOf()
    }

}

class FinalUpdate(
    override val where: String,
    override val boundWhere: String
) : FinalStatement(), FinalWhereStatement {

    var entity: Entity<*> by expected("Can't Use null entity") {
        it != null
    }

    val set by laziest {
        mutableListOf<Pair<Column<*>, Any>>()
    }

    val setSql by laziest {
        set.joinToString { (column, value) -> "${column.identifier} = $value" } // TODO Use value sql here
    }

    val boundSetSql by laziest {
        set.joinToString { (column, value) -> "${column.identifier} = ?" }
    }

    val prefixSql by laziest {
        "UPDATE ${entity.identifier} SET"
    }

    override val sql by laziest {
        "$prefixSql $setSql $where"
    }

    override val boundSql by laziest {
        "$prefixSql $boundSetSql $boundWhere"
    }

}