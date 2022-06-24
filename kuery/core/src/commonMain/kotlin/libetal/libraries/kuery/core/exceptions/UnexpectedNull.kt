package libetal.libraries.kuery.core.exceptions

import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.extensions.name

class UnexpectedNull(entity: Entity<*>, columnName: String) :
    RuntimeException("From ${entity.name}.$columnName. Make sure row isn't nullable")
