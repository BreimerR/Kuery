package libetal.libraries.kuery.annotations.columns

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.TYPE_PARAMETER, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.TYPE)
annotation class Varchar(
    val name: String = "",
    val default: String = "",
    val size: Int = 55,
    val collate: String = "",
    val uniqueOn: String = "",
    val unique: Boolean = false,
    val primary: Boolean = false
)





