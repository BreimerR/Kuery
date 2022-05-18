package libetal.libraries.kuery.plugin.sqlite.google

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

/** TODO
 * [README](https://kotlinlang.org/docs/ksp-multi-round.html#multiple-round-behavior)
 **/
class EntityProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor = EntityProcessor(environment)
}