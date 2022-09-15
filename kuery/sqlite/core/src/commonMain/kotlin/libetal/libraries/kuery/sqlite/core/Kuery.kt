package libetal.libraries.kuery.sqlite.core

import kotlinx.datetime.LocalDate
import libetal.kotlin.debug.info
import libetal.libraries.kuery.core.columns.*
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
import libetal.libraries.kuery.core.getOrRegisterColumn


/**
 * [SQLITE DataTypes](https:sqlite.org/datatypes3.html)
 * [SQLITE SPECS](https://www.sqlite.org/lang.html)
 **/
abstract class Kuery : CoreKuery<Entity<*, *>>(), ConnectorListener {

    abstract val entities: List<Entity<*, *>>

    init {
        connector = Connector().also {
            it.addListener(this)
        }

    }

    private val connection
        get() = connector ?: throw RuntimeException("Shouldn't ever be null")

    fun Entity<*, *>.text(
        name: String,
        size: Int = 55,
        default: String? = null,
        primary: Boolean = false
    ) = getOrRegisterColumn(name) {
        CharSequenceColumn<CharSequence, Int>(
            name,
            "TEXT",
            primary = false,
            nullable = false,
            default = default,
            size = size,
            alias = null
        ) { result ->
            result ?: throw MalformedStoredData(this, name)
        }
    }

    fun Entity<*, *>.nullableText(
        name: String,
        size: Int = 55,
        default: String? = null,
        primary: Boolean = false
    ) = getOrRegisterColumn(name) {
        CharSequenceColumn<CharSequence?, Int>(
            name,
            "TEXT",
            primary = false,
            nullable = true,
            default = default,
            size = size,
            alias = null
        ) { result ->
            result
        }
    }

    /**
     * Tables are objects
     * and they need some way to be initialized
     * as objects are singletons that are initialized
     * on the first use
     **/
    fun init() {
        entities.forEach {
            TAG info "Initialized $it"
        }
    }

    override fun Entity<*, *>.string(
        name: String,
        size: Int,
        default: String?,
        primary: Boolean
    ) = text(
        name,
        size,
        default,
        primary
    )

    override fun Entity<*, *>.nullableString(name: String, size: Int, default: String?) =
        nullableText(name, size, default)

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
    ) = getOrRegisterColumn(name) {
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

    fun Entity<*, *>.real(name: String = "") = getOrRegisterColumn(name) {
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

    fun Entity<*, *>.blob(name: String = "", primary: Boolean = false) = getOrRegisterColumn(name) {
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
    ): Column<Long> = numeric(name, "NUMERIC", default, size, primary, false, false, null) {
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

    fun Entity<*, *>.char(name: String, default: Char?) = getOrRegisterColumn(name) {
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
        getOrRegisterColumn(name) {
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
    override fun Entity<*, *>.boolean(name: String, default: Boolean?) = getOrRegisterColumn(name) {

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
        connection.query("$statement")
    }

    override infix fun query(statement: Create<*, *>) =
        connection.query(statement)

    override infix fun query(statement: Select) =
        connection.query(statement)

    override infix fun query(statement: Insert) =
        connection.query(statement)

    override infix fun query(statement: Delete) =
        connection.query(statement)

    override infix fun query(statement: Drop) =
        connection.query(statement)

    override infix fun query(statement: Update) =
        connection.query(statement)

    override fun onCreate(connector: Connector) {

        tableEntities.forEach { (entity, _) ->
            TAG info "Creating table ${entity::class}"

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


