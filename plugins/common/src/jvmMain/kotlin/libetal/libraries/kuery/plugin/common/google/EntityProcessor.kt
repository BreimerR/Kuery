package libetal.libraries.kuery.plugin.common.google

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import libetal.libraries.kuery.plugin.common.objects.Annotations

class EntityProcessor(environment: SymbolProcessorEnvironment) : com.google.devtools.ksp.processing.SymbolProcessor {

    val codeGenerator by lazy {
        environment.codeGenerator
    }

    val options by lazy {
        environment.options
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val sqliteDatabase = resolver.getSymbolsWithAnnotation(Annotations.SQLITE)
            .filterIsInstance<KSClassDeclaration>()

        TODO("Not sure of this yet")

    }



}