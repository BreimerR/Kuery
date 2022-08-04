package libetal.libraries.kuery.mariadb

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.Kuery as CoreKuery
import libetal.libraries.kuery.core.columns.CharSequenceColumn
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.columns.GenericColumn
import libetal.libraries.kuery.core.columns.NumberColumn
import libetal.libraries.kuery.core.exceptions.MalformedStoredData
import libetal.libraries.kuery.core.exceptions.UnexpectedNull
import libetal.libraries.kuery.core.statements.*
import libetal.libraries.kuery.core.statements.results.*
import libetal.libraries.kuery.mariadb.entities.TableEntity

abstract class Kuery(
    private val database: String = "test",
    private val user: String = "test",
    private val password: String = "",
    private val host: String = "localhost",
    private val port: UInt = 3306u
) : CoreKuery<TableEntity<*>>() {

    val connector: Connector by laziest {
        Connector(
            database,
            user,
            password,
            host,
            port
        )
    }

    override fun TableEntity<*>.long(
        name: String,
        size: Long?,
        default: Long?,
        primary: Boolean
    ) = numeric("LONG", name, size, primary, autoIncrement = false, default) {
        it.toLongOrNull() ?: throw MalformedStoredData(this, name)
    }

    fun TableEntity<*>.long(
        name: String,
        size: Long?,
        default: Long?,
        primary: Boolean,
        autoIncrement: Boolean = false
    ) = numeric("LONG", name, size, primary, autoIncrement, default) {
        it.toLongOrNull() ?: throw MalformedStoredData(this, name)
    }

    override fun TableEntity<*>.int(
        name: String,
        size: Int?,
        primary: Boolean,
        autoIncrement: Boolean,
        default: Int?
    ) = numeric("INT", name, size, false, autoIncrement = false, default) {
        it.toIntOrNull() ?: throw MalformedStoredData(this, name)
    }

    /**TODO
     * WATCH: JU JITSU  Nicholas Cage not add just take
     * OR:KILL CHAIN
     **/
    override fun TableEntity<*>.float(name: String, size: Float?, default: Float?) =
        numeric("FLOAT", name, size, false, autoIncrement = false, default) {
            it.toFloatOrNull() ?: throw MalformedStoredData(this, name)
        }

    fun <N> TableEntity<*>.numeric(
        type: String,
        name: String,
        size: N? = null,
        primary: Boolean = false,
        autoIncrement: Boolean = primary,
        default: N? = null,
        parser: (String) -> N
    ) = registerColumn(name) {
        NumberColumn(
            name = name,
            typeName = type,
            default = default,
            primary = primary,
            autoIncrement = autoIncrement,
            size = size,
            nullable = false,
            alias = null,
        ) { result ->
            result ?: throw UnexpectedNull(this, name)
            parser(result)
        }
    }

    override fun TableEntity<*>.char(name: String): Column<Char> =
        char(name, null)

    fun TableEntity<*>.char(name: String, default: Char?) = registerColumn(name) {
        CharSequenceColumn<Char, Int>(
            name = name,
            typeName = "CHAR",
            nullable = false,
            default = default
        ) {
            it?.toCharArray()?.getOrNull(0) ?: throw UnexpectedNull(this, name)
        }
    }

    override fun TableEntity<*>.date(name: String, default: LocalDate?, format: String?) = registerColumn(name) {
        GenericColumn(
            name,
            "DATE",
            nullable = false,
            toSqlString = {
                "'$it'"
            }
        ) { result ->
            result ?: throw UnexpectedNull(this, name)
            LocalDate.parse(result)
        }
    }

    override fun TableEntity<*>.string(
        name: String,
        size: Int,
        default: String?,
        primary: Boolean,
        nullable: Boolean
    ) =
        registerColumn(name) {
            CharSequenceColumn<CharSequence, Int>(
                name = name,
                typeName = "VARCHAR",
                nullable = false,
                default = default,
                size = size,
                primary = primary,
                alias = null
            ) { result ->
                result ?: throw UnexpectedNull(this, name)
            }
        }

    override fun TableEntity<*>.nullableString(name: String, size: Int, default: String?) = registerColumn(name) {
        CharSequenceColumn<CharSequence?, Int>(
            name = name,
            typeName = "VARCHAR",
            nullable = false,
            default = default,
            size = size,
            primary = false,
            alias = null
        ) { result ->
            result
        }
    }

    override fun TableEntity<*>.boolean(name: String, default: Boolean?) = registerColumn(name) {
        val defaultSql = default?.let { " DEFAULT ${if (it) "true" else "false"}" } ?: ""

        GenericColumn(name,
            "BOOLEAN",
            nullable = false,
            toSqlString = {
                if (it) "true" else "false"
            }
        ) { result ->
            result ?: throw UnexpectedNull(this, name)
            result.toBooleanStrictOrNull() ?: throw MalformedStoredData(this, name)
        }

    }

    override fun query(statement: Create<*, *>) =
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

    override fun <T : Result> execute(statement: Statement<T>) {
        connector.execute(statement)
    }

}