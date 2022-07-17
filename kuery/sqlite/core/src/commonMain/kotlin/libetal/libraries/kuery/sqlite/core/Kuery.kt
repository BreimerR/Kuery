package libetal.libraries.kuery.sqlite.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.datetime.LocalDate
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.EntityColumn
import libetal.libraries.kuery.core.exceptions.MalformedStoredData
import libetal.libraries.kuery.core.exceptions.UnexpectedNull
import libetal.libraries.kuery.core.statements.*
import libetal.libraries.kuery.core.statements.results.*
import libetal.libraries.kuery.core.Kuery as CoreKuery
import libetal.libraries.kuery.sqlite.core.database.extensions.addListener
import libetal.libraries.kuery.sqlite.core.database.listeners.ConnectorListener
import libetal.libraries.kuery.sqlite.core.entities.Entity
import libetal.libraries.kuery.core.tableEntities
import libetal.libraries.kuery.sqlite.core.database.Connector

/**
 * [SQLITE DataTypes](https:sqlite.org/datatypes3.html)
 * [SQLITE SPECS](https://www.sqlite.org/lang.html)
 **/
abstract class Kuery : CoreKuery<Entity<*, *, *>>(), ConnectorListener {

    val connector: Connector by laziest {
        init().also {
            it.addListener(this)
        }
    }

    abstract fun init(): Connector

    fun Entity<*, *, *>.text(name: String = "", default: String? = null) =
        registerColumn(name) {
            val defaultSql = default?.let { " DEFAULT '$default'" } ?: ""
            EntityColumn(it, "$it TEXT $NOT_NULL$defaultSql", primary = false, nullable = false, { value ->
                "'$value'"
            }) { result ->
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
            EntityColumn(
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

    fun Entity<*, *, *>.real(name: String = "", primary: Boolean = false) = registerColumn(name) { columnName ->
        EntityColumn(
            columnName,
            "`$name` REAL",
            primary,
            false,
            {
                "$it"
            }
        ) {
            it ?: throw UnexpectedNull(this, columnName)
            val actions = listOf(String::toDouble, String::toFloat, String::toLong, String::toInt)

            var result: Number? = null

            for (action in actions) {
                result = try {
                    action(it)
                } catch (e: Exception) {
                    null
                }
            }

            result ?: throw MalformedStoredData(this, columnName)
        }
    }

    fun Entity<*, *, *>.blob(name: String = "", primary: Boolean = false) = registerColumn(name) { columnName ->
        EntityColumn(
            columnName,
            "`$columnName` BLOB",
            primary,
            nullable = false,
            { it: Any ->
                it.toString()
            }
        ) {
            throw RuntimeException("Not sure of the representation for this one")
        }

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
            EntityColumn(
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
        registerColumn(name) { columnName ->
            EntityColumn(
                columnName,
                "`$columnName` TEXT",
                false,
                nullable = false,
                {
                    "'$it'"
                }
            ) { result ->
                result ?: throw UnexpectedNull(this, columnName)
                try {
                    LocalDate.parse(result)
                } catch (e: Exception) {
                    throw MalformedStoredData(this, columnName)
                }
            }
        }

    /** TODO
     * Not sure about the implementation of default
     * here yet
     * but can easily be changed once solved
     * "DEFAULT(strftime('%Y-%m-%dT%H:%M:%fZ', '$it'))"
     **/
    fun Entity<*, *, *>.date(name: String, default: LocalDate? = null, format: String? = null) =
        registerColumn(name) { columnName ->
            val defaultSql = default?.let { " DEFAULT(strftime('%Y-%m-%dT%H:%M:%fZ', '$it'))" } ?: ""
            EntityColumn(
                columnName,
                "`$columnName` TEXT$defaultSql $NOT_NULL",
                false,
                nullable = false,
                {
                    "'$it'"
                }
            ) { result ->
                result ?: throw UnexpectedNull(this, columnName)
                try {
                    LocalDate.parse(result)
                } catch (e: Exception) {
                    throw MalformedStoredData(this, columnName)
                }
            }
        }


    override fun Entity<*, *, *>.string(name: String, size: Int, default: String?) =
        registerColumn(name) { columnName ->
            EntityColumn(
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

    /**
     * SQLite doesn't support booleans
     * thus need to store this value as an integer and
     * retrieve as int and convert to boolean
     **/
    override fun Entity<*, *, *>.boolean(name: String, default: Boolean?) = registerColumn(name) { columnName ->
        val defaultSql = default?.let { " DEFAULT ${if (it) "1" else "0"}" } ?: ""

        EntityColumn(
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

    override fun <R : Result> execute(statement: Statement<R>) {
        connector.execute(statement)
    }

    override fun Create<*, *>.query(collector: CreateResult.() -> Unit) {
        runBlocking {
            connector.query(this@query).invoke(collector)
        }
    }

    override fun Select.query(collector: SelectResult.() -> Unit) {
        runBlocking {
            connector.query(this@query).invoke(collector)
        }
    }

    override fun Insert.query(collector: InsertResult.() -> Unit) {
        runBlocking {
            connector.query(this@query).invoke(collector)
        }
    }

    override fun Delete.query(collector: DeleteResult.() -> Unit) {
        runBlocking {
            connector.query(this@query).invoke(collector)
        }
    }

    override fun Drop.query(collector: DropResult.() -> Unit) {
        runBlocking {
            connector.query(this@query).invoke(collector)
        }
    }

    override fun Update.query(collector: UpdateResult.() -> Unit) {
        runBlocking {
            connector.query(this@query).invoke(collector)
        }
    }

    override fun onCreate(connector: Connector) = tableEntities.forEach { (entity, columns) ->
        val sql = INSERT
    }

}

expect fun <T> runBlocking(block: suspend CoroutineScope.() -> T): T
