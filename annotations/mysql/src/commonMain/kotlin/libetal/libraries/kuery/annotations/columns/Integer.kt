package libetal.libraries.kuery.annotations.columns

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.TYPE_PARAMETER, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.TYPE)
annotation class Integer(
    val name: String = "",
    val default: Int = 0,
    val size: Int = 55,
    val uniqueOn: String = "",
    val unique: Boolean = false,
    val primary: Boolean = false,
    val autoIncrement: Boolean = false
)
