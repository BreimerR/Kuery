package libetal.libraries.kuery.annotations.common.columns

/**
 * Basic column with
 * the type being picked from the data type
 * of the property
 *
 * @Table
 * data class User(
 *     @Column // this will later morph into a CharacterSequence
 *     val name: String,
 *
 * )
 **/
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.TYPE_PARAMETER, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.TYPE)
annotation class Column(
    val name: String = ""
)