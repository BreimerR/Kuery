package libetal.libraries.kuery.annotations.columns

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.TYPE_PARAMETER, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.TYPE)
annotation class Text(
    val name: String = ""
)