package libetal.libraries.kuery.core.expressions

import libetal.kotlin.laziest
import libetal.libraries.kuery.core.columns.BaseColumn
import libetal.libraries.kuery.core.statements.parseToSql

/**TODO
 * SQL Concat value to prefix and postfix
 **/
class DecoratedExpression<T : Any>(
    left: BaseColumn<T>,
    operator: Operators,
    right: T,
    prefix: String = "",
    postfix: String = ""
) : DecoratedExpressions<BaseColumn<T>, T>(left, operator, right, prefix, postfix) {

    private val rightValueSql by laziest{
        val value =  left parseToSql rightValue

        when(rightValue){
            is String -> if(value[0] == '\'' && value.last() == '\'') "'$prefix${value.substring(1,value.length - 1)}$postfix'"
            else value
            else -> value
        }
    }
    override val sql: String by laziest {
        "`${left.name}` $operator $rightValueSql"
    }

    override val boundSql: String by laziest {
        "`${left.name}` $operator ?"
    }

    override val columnValues: List<*> by laziest {
        listOf(
            prefix + rightValue + postfix
        )
    }

}

