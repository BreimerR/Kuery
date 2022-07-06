package libetal.libraries.kuery.core.statements

import libetal.kotlin.expected
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.identifier

class Insert<T, E : Entity<T>>(sql: String, entity: E) : Statement<T, E>(sql, entity) {
    override val boundSql: String
        get() = TODO("Not yet implemented")
}


class FinalInsert() : FinalStatement() {

    val values by laziest {
        mutableListOf<List<Any>>()
    }

    val valuesSql by laziest {
        val initial = values.joinToString("), (") { actualValues ->
            actualValues.joinToString(", ") { value -> value.toString() }
        }

        "($initial)"
    }

    val boundValuesSql by laziest {
        val initial = values.joinToString("), (") { actualValues ->
            actualValues.joinToString(", ") { "?" }
        }

        "($initial)"
    }

    var entity: Entity<*> by expected("Can't Use null entity") {
        it != null
    }

    private val prefixInsert by laziest {
        "INSERT INTO ${entity.identifier}($columnsSql)"
    }

    override val sql by laziest {
        "$prefixInsert $valuesSql"
    }

    override val boundSql by laziest {
        "$prefixInsert $boundValuesSql"
    }

}

