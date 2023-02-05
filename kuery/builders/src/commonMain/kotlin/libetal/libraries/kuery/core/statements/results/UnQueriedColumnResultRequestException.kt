package libetal.libraries.kuery.core.statements.results

import libetal.libraries.kuery.core.columns.Column

class UnQueriedColumnResultRequestException(column: Column<*>) : RuntimeException("""Column ${column.name} wasn't added to query statement.""")
