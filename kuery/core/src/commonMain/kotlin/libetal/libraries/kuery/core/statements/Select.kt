@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements

import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.entities.Entity

open class Select<T, E : Entity<T>>(override var sql: String, override var entity: E) : Statement<T, E>()
