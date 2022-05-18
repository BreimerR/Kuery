package libetal.libraries.kuery.sqlite.core

import kotlinx.datetime.LocalDate
import libetal.libraries.kuery.core.Kuery
import libetal.libraries.kuery.core.statements.CreateStatementFactory
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
abstract class KSQLite : Kuery<Entity<*, *, *>>(), ConnectorListener {

    val connector: Connector by lazy {
        init().also {
            it.addListener(this)
        }
    }

    abstract fun init(): Connector

    fun Entity<*, *, *>.text(name: String = "", default: String? = null) =
        registerColumn(name) {
            Text(it, this, default)
        }

    fun <N : Number> Entity<*, *, *>.numeric(name: String = "", size: N? = null, primary: Boolean = false) =
        registerColumn(name) { columnName ->
            NumberColumn(columnName, this, size, primary)
        }

    fun Entity<*, *, *>.real(name: String = "") = registerColumn(name) { columnName ->
        Real(columnName, this)
    }

    fun Entity<*, *, *>.blob(name: String = "", primary: Boolean = false) = registerColumn(name) { columnName ->
        Blob(columnName, this, primary)
    }

    @Suppress("UNCHECKED_CAST")
    fun Entity<*, *, *>.boolean(name: String = "", primary: Boolean = false) = registerColumn(name) { columnName ->
        BooleanColumn(columnName, this, primary)
    }

    @Suppress("UNCHECKED_CAST")
    override fun Entity<*, *, *>.long(name: String) =
        numeric<Long>(name)

    @Suppress("UNCHECKED_CAST")
    override fun Entity<*, *, *>.int(name: String, primary: Boolean) =
        numeric<Int>(name)

    @Suppress("UNCHECKED_CAST")
    override fun Entity<*, *, *>.float(name: String) =
        numeric<Float>(name)

    override fun Entity<*, *, *>.char(name: String) =
        char(name, null)

    fun Entity<*, *, *>.char(name: String, default: Char?) =
        registerColumn(name) {
            CharColumn(name, this, default)
        }

    override fun Entity<*, *, *>.date(name: String) =
        registerColumn(name) { usableName ->
            DateColumn(usableName, this, false)
        }

    fun Entity<*, *, *>.date(name: String, default: LocalDate? = null, format: String? = null) =
        registerColumn(name) { usableName ->
            DateColumn(usableName, this, false)
        }


    override fun Entity<*, *, *>.string(name: String, size: Int, default: String?) =
        registerColumn(name) {
            Text(it, this, default, size)
        }

    override fun Entity<*, *, *>.boolean(name: String) = registerColumn(name) { columnName ->
        BooleanColumn(columnName, this)
    }

    override fun <T, E : libetal.libraries.kuery.core.entities.Entity<T>> execute(statement: Statement<T, E>) {
        // Connector.INSTANCE.execute(statement)
    }

    override fun onCreate(connector: Connector) = tableEntities.forEach { (entity, columns) ->
        val sql = INSERT
    }

    @Suppress("PropertyName")
    val CREATE by lazy {
        CreateStatementFactory(this)
    }


}


