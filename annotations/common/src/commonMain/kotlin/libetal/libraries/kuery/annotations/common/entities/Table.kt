package libetal.libraries.kuery.annotations.common.entities

@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE_PARAMETER)
annotation class Table(val name: String = "")
