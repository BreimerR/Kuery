package libetal.libraries.kuery.annotations.columns

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.TYPE_PARAMETER, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.TYPE)
annotation class Year(
    val name: String = ""
)