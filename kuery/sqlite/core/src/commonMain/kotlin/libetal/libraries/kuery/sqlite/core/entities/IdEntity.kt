package libetal.libraries.kuery.sqlite.core.entities

import libetal.libraries.kuery.core.columns.EntityColumn

abstract class IdEntity<T> : Entity<T, Long, EntityColumn<Long>>()