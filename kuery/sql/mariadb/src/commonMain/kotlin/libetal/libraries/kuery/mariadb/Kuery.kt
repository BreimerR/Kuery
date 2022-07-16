package libetal.libraries.kuery.mariadb

import kotlinx.datetime.LocalDate
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.Kuery
import libetal.libraries.kuery.core.columns.EntityColumn
import libetal.libraries.kuery.core.exceptions.MalformedStoredData
import libetal.libraries.kuery.core.exceptions.UnexpectedNull
import libetal.libraries.kuery.core.statements.Statement
import libetal.libraries.kuery.core.statements.results.Result
import libetal.libraries.kuery.mariadb.entities.TableEntity

abstract class Kuery(
    private val database: String = "test",
    private val user: String = "test",
    private val password: String = "",
    private val host: String = "localhost",
    private val port: UInt = 3306u
) : Kuery<TableEntity<*>>() {

    val connector: Connector by laziest {
        Connector(
            database,
            user,
            password,
            host,
            port
        )
    }

    override fun TableEntity<*>.long(name: String) = long(name, false)

    fun TableEntity<*>.long(name: String, primary: Boolean) = registerColumn(name) { columnName ->
        EntityColumn(columnName, "$columnName INT ", primary, false) { result ->
            result ?: throw UnexpectedNull(this, columnName)
            result.toLongOrNull() ?: throw MalformedStoredData(this, columnName)
        }
    }

    override fun TableEntity<*>.int(name: String, size: Int?, primary: Boolean) = registerColumn(name) { columnName ->
        EntityColumn(columnName, "$columnName INT ", primary, false) { result ->
            result ?: throw UnexpectedNull(this, columnName)
            result.toIntOrNull() ?: throw MalformedStoredData(this, columnName)
        }
    }

    /**TODO
     * WATCH: JU JITSU  Nicholas Cage not add just take
     * OR:KILL CHAIN
     **/
    override fun TableEntity<*>.float(name: String, size: Float?, default: Float?) =
        registerColumn(name) { columnName ->
            val defaultSql = default?.let { " DEFAULT $default" } ?: ""
            val maxSql = size?.let { "($size) " } ?: ""
            EntityColumn(columnName, "$columnName FLOAT$maxSql$defaultSql", false, false) { result ->
                result ?: throw UnexpectedNull(this, columnName)
                result.toFloatOrNull() ?: throw MalformedStoredData(this, columnName)
            }
        }

    override fun TableEntity<*>.char(name: String) =
        char(name, null)

    fun TableEntity<*>.char(name: String, default: Char?) = registerColumn(name) { columnName ->
        val defaultSql = default?.let { " DEFAULT '$it'" } ?: ""
        EntityColumn(
            columnName,
            sql = "`$columnName` CHAR$defaultSql",
            primary = false,
            nullable = false
        ) {
            it?.toCharArray()?.getOrNull(0) ?: throw UnexpectedNull(this, columnName)
        }
    }

    override fun TableEntity<*>.date(name: String) = registerColumn(name) { columnName ->
        EntityColumn(
            columnName,
            "`$columnName` DATE $NOT_NULL",
            primary = false,
            nullable = false,
            {
                "'$it'"
            }
        ) { result ->
            result ?: throw UnexpectedNull(this, columnName)
            LocalDate.parse(result)
        }
    }

    fun TableEntity<*>.date(
        name: String,
        default: LocalDate,
        format: String
    ) = registerColumn(name) { columnName ->
        val defaultSql = " DEFAULT STR_TO_DATE('$default','$format')"
        EntityColumn(
            columnName,
            "`$columnName` DATE$defaultSql $NOT_NULL",
            primary = false,
            nullable = false,
            {
                "'$it'"
            }
        ) { result ->
            result ?: throw UnexpectedNull(this, columnName)
            LocalDate.parse(result)
        }
    }

    override fun TableEntity<*>.string(name: String, size: Int, default: String?): EntityColumn<String> =
        registerColumn(name) { columnName ->
            val defaultSql = default?.let { " DEFAULT $default" } ?: ""
            val maxSql = "($size)"
            EntityColumn(
                columnName,
                "`$columnName` VARCHAR$maxSql$defaultSql $NOT_NULL",
                primary = false,
                nullable = false,
                {
                    "'$it'"
                }
            ) { result ->
                result ?: throw UnexpectedNull(this, columnName)
            }
        }

    override fun TableEntity<*>.nullableString(name: String, size: Int, default: String?): EntityColumn<String?> =
        registerColumn(name) { columnName ->
            val maxSql = "($size)"
            val defaultSql = default?.let { " DEFAULT '$it'" } ?: ""
            EntityColumn(
                columnName,
                "`$columnName` VARCHAR$maxSql$defaultSql",
                primary = false,
                nullable = false,
                {
                    it?.let {
                        "'$it'"
                    } ?: ""
                }
            ) { result ->
                result
            }
        }

    override fun TableEntity<*>.boolean(name: String, default: Boolean?) = registerColumn(name) { columnName ->
        val defaultSql = default?.let { " DEFAULT ${if (it) "true" else "false"}" } ?: ""

        EntityColumn(columnName,
            "$columnName BOOLEAN$defaultSql",
            primary = false,
            nullable = false,
            {
                if (it) "true" else "false"
            }
        ) { result ->
            result ?: throw UnexpectedNull(this, columnName)
            result.toBooleanStrictOrNull() ?: throw MalformedStoredData(this, columnName)
        }

    }

    override fun <T : Result> execute(statement: Statement<T>) {
        connector.execute<T>(statement)
    }

}