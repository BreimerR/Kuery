package libetal.libraries.kuery.core.statements

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.EntityColumn
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.identifier
import libetal.libraries.kuery.core.statements.results.InsertResult

class Insert(val entity: Entity<*>, vararg val columns: EntityColumn<*>) : ArgumentsStatement<InsertResult>() {

    private val values by laziest {
        mutableListOf<List<Any?>>()
    }

    private val columnsSql by laziest {
        "`${columns.joinToString("`, `") { it.name }}`"
    }

    private val valuesSql by laziest {
        var i = 0
        var row = 0
        val initialValues = values.joinToString("), (") { actualValues ->
            val sql = actualValues.joinToString(", ") { value ->
                val c = i
                val column = columns[i++]
                if (i == columns.size) i = 0
                val sql = column.parseToSqlError(value, "Invalid value passed for row: $row  column: $c value: $value")
                sql
            }
            row++
            sql
        }
        "($initialValues)"
    }

    private val boundValuesSql by laziest {
        val initial = values.joinToString("), (") { actualValues ->
            actualValues.joinToString(", ") { "?" }
        }

        "($initial)"
    }

    private val prefixInsert by laziest {
        "INSERT INTO ${entity.identifier}($columnsSql)"
    }

    override val sql by laziest {
        "$prefixInsert VALUES $valuesSql"
    }

    override val boundSql by laziest {
        "$prefixInsert VALUES $boundValuesSql"
    }

    fun row(vararg value: Any?) {
        if (value.size != columns.size) throw RuntimeException("Inserted lesser values than passed columns")
        val row = listOf(*value)
        values.add(row)
    }

    operator fun invoke(vararg value: Any?) {
        if (value.size != columns.size) throw RuntimeException("Inserted lesser values than passed columns")
    }

}

