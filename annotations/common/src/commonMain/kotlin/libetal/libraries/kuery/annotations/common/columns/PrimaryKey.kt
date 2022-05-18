package libetal.libraries.kuery.annotations.common.columns

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.TYPE_PARAMETER, AnnotationTarget.VALUE_PARAMETER)
annotation class PrimaryKey(
    val name: String = ""
)