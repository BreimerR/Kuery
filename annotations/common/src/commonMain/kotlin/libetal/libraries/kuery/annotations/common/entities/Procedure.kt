package libetal.libraries.kuery.annotations.common.entities

@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class Procedure(val procedure: String = "")