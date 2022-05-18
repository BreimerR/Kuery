package libetal.libraries.kuery.core.entities

abstract class Entity<T> {

    /**
     * Implementation of this property as a function
     * will reduce override conflicts
     **/
    abstract fun getEntityTableName(): String

    /**
     * Implementation of this property as a function
     * will reduce override conflicts
     **/
    open fun entityTableType() = Type.TABLE

    enum class Type(val identifier: String) {
        TABLE("TABLE"),
        VIEW("VIEW");

        override fun toString(): String = identifier
    }

}