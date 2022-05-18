package libetal.libraries.kuery.core.statements.builders

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.statements.Select

class SelectStatementBuilder<T, E : Entity<T>>(postFix: String, entity: E) :
    EntityStatementBuilder<T, E, Select<T, E>>("SELECT $postFix", entity = entity) {

    override fun build(extras: String) = Select("${this.sql} $extras", entity)

}



