package libetal.libraries.kuery.sqlite.core.database

import kotlinx.cinterop.ByteVarOf
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CPointerVarOf

typealias ColumnNamesArray = CPointer<CPointerVarOf<CPointer<ByteVarOf<Byte>>>>