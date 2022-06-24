@file:Suppress("FunctionName")

package libetal.libraries.kuery.core.statements

import libetal.libraries.kuery.core.entities.Entity

open class Select<T, E : Entity<T>>(sql: String, entity: E) : Statement<T, E>(sql, entity)
