package libetal.libraries.kuery.core.statements.results

import libetal.libraries.kuery.core.columns.Column

class UnQueriedColumnResultRequest(column: Column<*>) : Exception("""Column ${column.name} wasn't added to query statement.""")
