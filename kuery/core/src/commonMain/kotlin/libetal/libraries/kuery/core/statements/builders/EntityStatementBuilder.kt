package libetal.libraries.kuery.core.statements.builders

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.entities.extensions.name
import libetal.libraries.kuery.core.expressions.Expression
import libetal.libraries.kuery.core.expressions.WhereScope
import libetal.libraries.kuery.core.statements.Statement

interface EntityStatementBuilder<T, E : Entity<T>, S : Statement> : StatementBuilder

