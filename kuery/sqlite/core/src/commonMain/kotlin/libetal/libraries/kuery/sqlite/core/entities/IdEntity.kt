package libetal.libraries.kuery.sqlite.core.entities

import libetal.libraries.kuery.core.columns.Column

abstract class IdEntity<T> : Entity<T, Long, Column<Long>>()