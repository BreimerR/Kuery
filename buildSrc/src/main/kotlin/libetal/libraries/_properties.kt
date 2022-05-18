package libetal.libraries

import org.gradle.api.Project
import org.gradle.kotlin.dsl.provideDelegate
import kotlin.reflect.KProperty

operator fun <R> Project.invoke(converter: (Any) -> R) = PropertyDelegate(this, converter)

class PropertyDelegate<R>(private val project: Project, val converter: (Any) -> R) {

    operator fun getValue(receiver: Any, property: KProperty<*>): R {

        val delegate = project.provideDelegate(receiver, property)

        val value = try {
            delegate.getValue<Any?>(receiver, property)
        } catch (e: Exception) {
            null
        }

        return converter(
            value ?: throw RuntimeException(
                """|Missing property ${property.name} in file ${project.projectDir.path}/gradle.properties
                   |```
                   |${property.name} = REPLACE_WITH_VALUE
                   |```
                """.trimMargin()
            )
        )
    }
}