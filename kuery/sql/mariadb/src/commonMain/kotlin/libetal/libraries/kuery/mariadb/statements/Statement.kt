package libetal.libraries.kuery.mariadb.statements

import libetal.kotlin.laziest

abstract class Statement<T, E : libetal.libraries.kuery.core.entities.Entity<T>> :
    libetal.libraries.kuery.core.statements.Statement<T, E>() {

    // not sure about this being Any
    val boundParameters by laziest {
        mutableListOf<Any>()
    }

}