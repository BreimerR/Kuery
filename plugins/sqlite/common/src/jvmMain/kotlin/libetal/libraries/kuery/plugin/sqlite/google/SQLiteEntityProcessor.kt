package libetal.libraries.kuery.plugin.sqlite.google

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import libetal.libraries.kuery.plugin.common.google.EntityProcessor

class SQLiteEntityProcessor(environment: SymbolProcessorEnvironment) : EntityProcessor(environment) {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val files = resolver.getAllFiles()

        val packageName = options["packageName"] ?: error(
            """|Check KSP Documentation and provide argument [packageName] and a [value]
               |[URL](https://kotlinlang.org/docs/ksp-quickstart.html#pass-options-to-processors)
            """.trimMargin()
        )

        val toParseLater = mutableListOf<KSAnnotated>()

        for (file in files) {
            val visitor = FileVisitor(resolver, environment.logger, codeGenerator, packageName, options)
            file.accept(visitor) { annotated: KSClassDeclaration? ->
                annotated?.let { toParseLater.add(it) }
            }
        }

        return toParseLater.toList()
    }

}