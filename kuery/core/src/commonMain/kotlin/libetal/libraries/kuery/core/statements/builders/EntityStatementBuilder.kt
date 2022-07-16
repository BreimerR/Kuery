package libetal.libraries.kuery.core.statements.builders

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.Statement

interface EntityStatementBuilder<T, E : Entity<T>, S : Statement<*>> : StatementBuilder

