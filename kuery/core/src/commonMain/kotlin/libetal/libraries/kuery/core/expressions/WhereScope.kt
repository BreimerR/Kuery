package libetal.libraries.kuery.core.expressions

import libetal.libraries.kuery.core.columns.Column
import libetal.libraries.kuery.core.columns.extensions.lessOrEqual as columnLesOrEqual

class WhereScope : StatementScope() {

    // TODO add OR support with joins
    infix fun <T> Column<T>.lessOrEqual(value: T): Expression = columnLesOrEqual(value)

}

