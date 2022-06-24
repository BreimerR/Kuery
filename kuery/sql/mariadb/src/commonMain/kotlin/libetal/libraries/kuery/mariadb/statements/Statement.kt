package libetal.libraries.kuery.mariadb.statements

import libetal.kotlin.laziest

/*TODO MEANT TO BE THE BOUND VERSION NOT SURE IF TO DEFAULT TO BOUND STATEMENTS OR NOT YET*/
abstract class Statement<Class, E : libetal.libraries.kuery.core.entities.Entity<Class>>(sql: String, entity: E) :
    libetal.libraries.kuery.core.statements.Statement<Class, E>(sql, entity) {

    // not sure about this being Any
    val boundParameters by laziest {
        mutableListOf<Any>()
    }

}