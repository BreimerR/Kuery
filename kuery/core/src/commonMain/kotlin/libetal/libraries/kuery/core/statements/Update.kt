package libetal.libraries.kuery.core.statements

import libetal.libraries.kuery.core.entities.Entity

class Update<T, E : Entity<T>>(sql: String, entity: E) : Statement<T, E>(sql, entity)