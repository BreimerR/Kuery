package libetal.libraries.kuery.sqlite.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.datetime.LocalDate
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.*
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
abstract class Kuery : CoreKuery<Entity<*, *>>(), ConnectorListener {

    val connector: Connector by laziest {
        init().also {
            it.addListener(this)
        }
    }

    abstract fun init(): Connector

    fun Entity<*, *>.text(name: String = "", default: String? = null, size: Int? = null, nullable: Boolean = false) =
        registerColumn(name) {
            CharSequenceColumn(name, "TEXT", default, size, nullable = nullable) { result ->
                result ?: throw MalformedStoredData(this, name)
            }
        }

    fun <N : Number> Entity<*, *>.numeric(
        name: String,
        typeName: String,
        default: N? = null,
        size: N? = null,
        primary: Boolean = false,
        autoIncrement: Boolean = primary,
        nullable: Boolean = false,
        alias: String? = null,
        parser: (String) -> N
    ) = registerColumn(name) {
        NumberColumn(
            name,
            typeName,
            default,
            size,
            primary,
            autoIncrement,
            nullable,
            alias
        ) {
            it ?: throw UnexpectedNull(this, name)
            parser(it)
        }
    }

    fun Entity<*, *>.real(name: String = "") = registerColumn(name) {
        GenericColumn(
            name,
            "REAL",
            default = false,
            nullable = false,
            alias = null,
            toSqlString = {
                "'$it'"
            }
        ) { dbResult ->
            dbResult ?: throw UnexpectedNull(this, name)
            val actions = listOf(String::toDoubleOrNull, String::toFloatOrNull, String::toLongOrNull, String::toIntOrNull)

            var result: Number? = null

            for (action in actions) {
                result = action(dbResult)
            }

            result ?: dbResult

        }
    }

    fun Entity<*, *>.blob(name: String = "", primary: Boolean = false) = registerColumn(name) {
        GenericColumn(
            name,
            "`$name` BLOB",
            nullable = false
        ) {
            throw RuntimeException("Not sure of the representation for this one")
        }

    }

    @Suppress("UNCHECKED_CAST")
    override fun Entity<*, *>.long(
        name: String,
        size: Long?,
        default: Long?,
        primary: Boolean
    ) = numeric(name, "NUMERIC", default, size, primary, false, false, null) {
        it.toLongOrNull() ?: throw MalformedStoredData(this, name)
    }

    @Suppress("UNCHECKED_CAST")
    override fun Entity<*, *>.int(
        name: String,
        size: Int?,
        primary: Boolean,
        autoIncrement: Boolean,
        default: Int?
    ) = numeric(name, "INTEGER", default, size, primary, autoIncrement, false, null) {
        it.toIntOrNull() ?: throw MalformedStoredData(this, name)
    }

    @Suppress("UNCHECKED_CAST")
    override fun Entity<*, *>.float(name: String, size: Float?, default: Float?) =
        numeric(name, "", default, size, false, false, false, null) {
            it.toFloatOrNull() ?: throw MalformedStoredData(this, name)
        }

    override fun Entity<*, *>.char(name: String): Column<Char> = char(name, null)

    fun Entity<*, *>.char(name: String, default: Char?) = registerColumn(name) {
        CharSequenceColumn(
            name,
            "TEXT",
            default = default,
            size = null,
            nullable = false,
        ) {
            it?.toCharArray()?.getOrNull(0) ?: throw UnexpectedNull(this, name)
        }
    }

    override fun Entity<*, *>.date(name: String) =
        registerColumn(name) {
            GenericColumn(
                name = name,
                typeName = "TEXT",
                default = null,
                nullable = false,
                toSqlString = {
                    "'$it'"/*TODO DATE_CONVERTER('$it')*/
                },
                alias = null
            ) { result ->
                result ?: throw UnexpectedNull(this, name)
                try {
                    LocalDate.parse(result)
                } catch (e: Exception) {
                    throw MalformedStoredData(this, name)
                }
            }
        }

    /** TODO
     * Not sure about the implementation of default
     * here yet
     * but can easily be changed once solved
     * "DEFAULT(strftime('%Y-%m-%dT%H:%M:%fZ', '$it'))"
     **/
    fun Entity<*, *>.date(name: String, default: LocalDate? = null, format: String? = null) =
        registerColumn(name) {
            val defaultSql = default?.let { " DEFAULT(strftime('%Y-%m-%dT%H:%M:%fZ', '$it'))" } ?: ""
            GenericColumn(
                name = name,
                typeName = "TEXT",
                default = null,
                nullable = false,
                toSqlString = {
                    "'$it'"/*TODO DATE_CONVERTER('$it')*/
                },
                alias = null
            ) { result ->
                result ?: throw UnexpectedNull(this, name)
                try {
                    LocalDate.parse(result)
                } catch (e: Exception) {
                    throw MalformedStoredData(this, name)
                }
            }
        }


    override fun Entity<*, *>.string(name: String, size: Int, default: String?): Column<String> = registerColumn(name) {
        CharSequenceColumn(
            name,
            "TEXT",
            primary = false,
            nullable = false,
            default = default,
            size = size,
            alias = null
        ) {
            it ?: throw UnexpectedNull(this, name)
        }
    }

    /**
     * SQLite doesn't support booleans
     * thus need to store this value as an integer and
     * retrieve as int and convert to boolean
     **/
    override fun Entity<*, *>.boolean(name: String, default: Boolean?) = registerColumn(name) {

        GenericColumn(
            name = name,
            typeName = "NUMERIC",
            nullable = false,
            default = default,
            toSqlString = {
                if (it) "1" else "0"
            }
        ) { result ->
            result ?: throw UnexpectedNull(this, name)
            result.toIntOrNull()?.let {
                when (it) {
                    1 -> true
                    0 -> false
                    else -> null
                }
            } ?: throw MalformedStoredData(this, name)
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

    }

}

expect fun <T> runBlocking(block: suspend CoroutineScope.() -> T): T
