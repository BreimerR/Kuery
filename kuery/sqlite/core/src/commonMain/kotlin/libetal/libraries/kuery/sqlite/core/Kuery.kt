package libetal.libraries.kuery.sqlite.core

import kotlinx.datetime.LocalDate
import libetal.kotlin.debug.info
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.CharSequenceColumn
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.columns.GenericColumn
import libetal.libraries.kuery.core.columns.NumberColumn
import libetal.libraries.kuery.core.entities.TableEntity
import libetal.libraries.kuery.core.entities.ViewEntity
import libetal.libraries.kuery.core.entities.extensions.type
import libetal.libraries.kuery.core.exceptions.MalformedStoredData
import libetal.libraries.kuery.core.exceptions.UnexpectedNull
import libetal.libraries.kuery.core.statements.*
import libetal.libraries.kuery.core.statements.results.*
import libetal.libraries.kuery.core.tableEntities
import libetal.libraries.kuery.sqlite.core.database.Connector
import libetal.libraries.kuery.sqlite.core.database.extensions.addListener
import libetal.libraries.kuery.sqlite.core.database.listeners.ConnectorListener
import libetal.libraries.kuery.sqlite.core.entities.Entity
import libetal.libraries.kuery.sqlite.coroutines.runBlocking
import libetal.libraries.kuery.core.Kuery as CoreKuery
import libetal.libraries.kuery.core.Connector as CoreConnector

/**
 * [SQLITE DataTypes](https:sqlite.org/datatypes3.html)
 * [SQLITE SPECS](https://www.sqlite.org/lang.html)
 **/
abstract class Kuery : CoreKuery<Entity<*, *>>(), ConnectorListener {

    val connector: CoreConnector by laziest {
        Connector().also {
            it.addListener(this)
        }
    }

    fun Entity<*, *>.text(
        name: String,
        size: Int = 55,
        default: String? = null,
        primary: Boolean = false,
        nullable: Boolean = true
    ) = registerColumn(name) {
        CharSequenceColumn<CharSequence, Int>(
            name,
            "TEXT",
            primary = false,
            nullable = nullable,
            default = default,
            size = size,
            alias = null
        ) { result ->
            result ?: throw MalformedStoredData(this, name)
        }
    }

    override fun Entity<*, *>.string(
        name: String,
        size: Int,
        default: String?,
        primary: Boolean,
        nullable: Boolean
    ) = text(
        name,
        size,
        default,
        primary,
        nullable
    )

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
            if (primary) {
                when (name) {
                    "id", "ID", "Id", "iD", "_id" -> "_id"
                    /**
                     * TODO
                     * This is bad I know.
                     * Solution to be rethought
                     **/
                    else -> throw RuntimeException("Android Doesn't support having id as id has to be _id")
                }
            } else name,
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
            val actions =
                listOf(String::toDoubleOrNull, String::toFloatOrNull, String::toLongOrNull, String::toIntOrNull)

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

    /** TODO
     * Not sure about the implementation of default
     * here yet
     * but can easily be changed once solved
     * "DEFAULT(strftime('%Y-%m-%dT%H:%M:%fZ', '$it'))"
     **/
    override fun Entity<*, *>.date(name: String, default: LocalDate?, format: String?) =
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
        connector.query("$statement")
    }

    override infix fun query(statement: Create<*, *>) =
        connector.query(statement)

    override infix fun query(statement: Select) =
        connector.query(statement)

    override infix fun query(statement: Insert) =
        connector.query(statement)

    override infix fun query(statement: Delete) =
        connector.query(statement)

    override infix fun query(statement: Drop) =
        connector.query(statement)

    override infix fun query(statement: Update) =
        connector.query(statement)

    override fun onCreate(connector: Connector) {

        tableEntities.forEach { (entity, _) ->
            TAG info "Creating table $entity"

            val statement = when (entity.type) {
                libetal.libraries.kuery.core.entities.Entity.Type.TABLE -> CREATE TABLE entity as TableEntity
                libetal.libraries.kuery.core.entities.Entity.Type.VIEW -> CREATE VIEW entity as ViewEntity
            }

            runBlocking {

                statement query {
                    if (error != null) {
                        throw RuntimeException(error)
                    } else TAG info "Created Table $entity"
                }

                entity.onCreate()

            }
        }

    }

    companion object {
        const val TAG = "KuerySqlite"
    }

}


