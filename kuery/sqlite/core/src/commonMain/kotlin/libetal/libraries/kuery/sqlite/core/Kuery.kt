package libetal.libraries.kuery.sqlite.core

import kotlinx.datetime.LocalDate
import libetal.libraries.kuery.core.columns.FinalColumn
import libetal.libraries.kuery.core.exceptions.MalformedStoredData
import libetal.libraries.kuery.core.exceptions.UnexpectedNull
import libetal.libraries.kuery.core.Kuery as CoreKuery
import libetal.libraries.kuery.core.statements.INSERT
import libetal.libraries.kuery.core.statements.Statement
import libetal.libraries.kuery.sqlite.core.database.extensions.addListener
import libetal.libraries.kuery.sqlite.core.database.listeners.ConnectorListener
import libetal.libraries.kuery.sqlite.core.entities.Entity
import libetal.libraries.kuery.core.tableEntities
import libetal.libraries.kuery.sqlite.core.columns.*
import libetal.libraries.kuery.sqlite.core.database.Connector

/**
 * [SQLITE DataTypes](https:sqlite.org/datatypes3.html)
 * [SQLITE SPECS](https://www.sqlite.org/lang.html)
 **/
abstract class Kuery : CoreKuery<Entity<*, *, *>>(), ConnectorListener {

    val connector: Connector by lazy {
        init().also {
            it.addListener(this)
        }
    }

    abstract fun init(): Connector

    fun Entity<*, *, *>.text(name: String = "", default: String? = null) =
        registerColumn(name) {
            FinalColumn(it, "$it TEXT", primary = false, nullable = false) { result ->
                result ?: throw MalformedStoredData(this, it)
            }
        }

    fun <N : Number> Entity<*, *, *>.numeric(
        name: String = "",
        size: N? = null,
        primary: Boolean = false,
        parser: (String, String?) -> N
    ) =
        registerColumn(name) { columnName ->
            val sizeSql = size?.let { "($size)" } ?: ""
            FinalColumn(
                columnName,
                "$columnName NUMERIC$sizeSql $NOT_NULL",
                primary,
                nullable = false, {
                    it.toString()
                }
            ) {
                parser(columnName, it)
            }
        }

    fun Entity<*, *, *>.real(name: String = "") = registerColumn(name) { columnName ->
        Real(columnName, false)
    }

    fun Entity<*, *, *>.blob(name: String = "", primary: Boolean = false) = registerColumn(name) { columnName ->
        Blob(columnName, primary)
        FinalColumn(
            columnName,
            "`$columnName` BLOB",
            primary,
            nullable = false,
            {
                it.toString()
            }
        ) {
            throw RuntimeException("Not sure of the representation for this one")
            Any()
        }

    }

    @Suppress("UNCHECKED_CAST")
    fun Entity<*, *, *>.boolean(name: String = "", primary: Boolean = false) = registerColumn(name) { columnName ->
        BooleanColumn(columnName, primary)
    }

    @Suppress("UNCHECKED_CAST")
    override fun Entity<*, *, *>.long(name: String) = numeric(name) { columnName, result ->
        result ?: throw UnexpectedNull(this, columnName)
        result.toLongOrNull() ?: throw MalformedStoredData(this, columnName)
    }

    @Suppress("UNCHECKED_CAST")
    override fun Entity<*, *, *>.int(name: String, size: Int?, primary: Boolean) =
        numeric(name, size) { columnName, result ->
            result ?: throw UnexpectedNull(this, columnName)
            result.toIntOrNull() ?: throw MalformedStoredData(this, columnName)
        }

    @Suppress("UNCHECKED_CAST")
    override fun Entity<*, *, *>.float(name: String, size: Float?, default: Float?) =
        numeric(name, size) { columnName, result ->
            result ?: throw UnexpectedNull(this, columnName)
            result.toFloatOrNull() ?: throw MalformedStoredData(this, columnName)
        }

    override fun Entity<*, *, *>.char(name: String) =
        char(name, null)

    fun Entity<*, *, *>.char(name: String, default: Char?) =
        registerColumn(name) { columnName ->
            FinalColumn(
                columnName,
                "`$columnName` TEXT",
                primary = false,
                nullable = false,
                {
                    "'$it'"
                }
            ) {
                it?.toCharArray()?.getOrNull(0) ?: throw UnexpectedNull(this, columnName)
            }
        }

    override fun Entity<*, *, *>.date(name: String) =
        registerColumn(name) { usableName ->
            DateColumn(usableName, false)
        }

    fun Entity<*, *, *>.date(name: String, default: LocalDate? = null, format: String? = null) =
        registerColumn(name) { usableName ->
            DateColumn(usableName, false)
        }


    override fun Entity<*, *, *>.string(name: String, size: Int, default: String?) =
        registerColumn(name) { columnName ->
            FinalColumn(
                columnName,
                "`$columnName` TEXT",
                primary = false,
                nullable = false,
                {
                    "'$it'"
                }
            ) {
                it ?: throw UnexpectedNull(this, columnName)
            }
        }

    override fun Entity<*, *, *>.boolean(name: String, default: Boolean?) = registerColumn(name) { columnName ->
        val defaultSql = default?.let { " DEFAULT ${if (it) "1" else "0"}" } ?: ""

        FinalColumn(
            columnName,
            "`$columnName` NUMBER$defaultSql $NOT_NULL",
            primary = false,
            nullable = false,
            {
                if (it) "1" else "0"
            }
        ) { result ->
            result ?: throw UnexpectedNull(this, columnName)
            result.toIntOrNull()?.let {
                when (it) {
                    1 -> true
                    0 -> false
                    else -> null
                }
            } ?: throw MalformedStoredData(this, columnName)
        }
    }

    override fun <T, E : libetal.libraries.kuery.core.entities.Entity<T>> execute(statement: Statement<T, E>) {
        // Connector.INSTANCE.execute(statement)
    }

    override fun onCreate(connector: Connector) = tableEntities.forEach { (entity, columns) ->
        val sql = INSERT
    }

    /* val CREATE by lazy {
         CreateStatementFactory(this)
     }*/


}


