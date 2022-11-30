package libetal.libraries.kuery.sqlite.core.entities

import libetal.libraries.kuery.core.columns.BaseColumn
import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.entities.Entity
import libetal.libraries.kuery.core.entities.TableEntity

/**
 * All Tables in sqlite have an index
 * column to improve performance
 **/
abstract class Entity<DataClass, IdType> : TableEntity<DataClass>() {
    abstract val id: BaseColumn<IdType>
}



