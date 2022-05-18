package libetal.libraries.kuery.mariadb

import kotlinx.datetime.LocalDate
import libetal.kotlin.laziest
import libetal.libraries.kuery.core.Kuery
import libetal.libraries.kuery.core.columns.BooleanColumn
import libetal.libraries.kuery.core.statements.Statement
import libetal.libraries.kuery.mariadb.columns.*
import libetal.libraries.kuery.mariadb.entities.Entity

abstract class Kuery(
    private val database: String = "test",
    private val user: String = "test",
    private val password: String = "",
    private val host: String = "localhost",
    private val port: UInt = 3306u
) : Kuery<Entity<*>>() {

    val connector: Connector by laziest {
        Connector(
            database,
            user,
            password,
            host,
            port
        )
    }

    override fun Entity<*>.long(name: String) = long(name, false)

    fun Entity<*>.long(name: String, primary: Boolean) =
        registerColumn(name) { usableEntityName ->
            LongColumn(usableEntityName, this, primary = primary)
        }

    override fun Entity<*>.int(name: String, primary: Boolean) =
        int(name, null)

    fun Entity<*>.int(name: String, size: Int? = null, primary: Boolean = false) =
        registerColumn(name) { usableName ->
            IntColumn(usableName, this, size, primary)
        }

    override fun Entity<*>.float(name: String) =
        float(name, null, null)

    /**TODO
     * WATCH: JU JITSU  Nicholas Cage not add just take
     * OR:KILL CHAIN
     **/
    fun Entity<*>.float(name: String, size: Float? = null, default: Float? = null) =
        registerColumn(name) { usableName ->
            FloatColumn(usableName, this, size, default)
        }

    override fun Entity<*>.char(name: String) =
        char(name, null)

    fun Entity<*>.char(name: String, default: Char?) = registerColumn(name) { usableName ->
        CharColumn(usableName, this, default)
    }

    override fun Entity<*>.date(name: String) =
        registerColumn(name) { usableName ->
            DateColumn(usableName, this)
        }

    fun Entity<*>.date(
        name: String,
        default: LocalDate? = null,
        format: String? = null
    ) =
        registerColumn(name) { usableName ->
            DateColumn(
                usableName,
                this,
                default,
                default?.let { format ?: throw RuntimeException("Can't set date without a date format") })
        }

    override fun Entity<*>.string(name: String, size: Int, default: String?) =
        registerColumn(name) { usableName ->
            StringColumn(usableName, this, size, default)
        }

    // Something missing here forgetting
    fun Entity<*>.string(name: String, size: Int, primary: Boolean = false) =
        registerColumn(name) { usableName ->
            StringColumn(usableName, this, size, primary)
        }

    override fun Entity<*>.boolean(name: String) =
        boolean(name, false)

    fun Entity<*>.boolean(name: String, default: Boolean) =
        registerColumn(name) { usableName ->
            BooleanColumn(usableName, this, default)
        }

    override fun <T, E : libetal.libraries.kuery.core.entities.Entity<T>> execute(statement: Statement<T, E>) {
        connector.execute(statement)
    }


}