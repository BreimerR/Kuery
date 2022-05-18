package libetal.libraries.kuery.plugin.sqlite.google

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate
import libetal.libraries.kuery.plugin.common.google.Processor
import libetal.libraries.kuery.plugin.common.objects.Annotations

class EntityProcessor(environment: SymbolProcessorEnvironment) : Processor(environment) {

    var databaseParsed = false

    override fun process(resolver: Resolver): List<KSAnnotated> {

        val unprocessed = mutableListOf<KSAnnotated>()

        val sqliteDatabase = resolver.getSymbolsWithAnnotation(Annotations.SQLITE)
            .filterIsInstance<KSClassDeclaration>().toList().filter { it.validate() }

        if (!databaseParsed)
            databaseParsed = sqliteDatabase.firstOrNull()?.parseDatabase() ?: false

        val tables =
            resolver.getSymbolsWithAnnotation(Annotations.TABLE).filterIsInstance<KSClassDeclaration>().toList()
                .groupBy { it.validate() }

        tables[false]?.forEach {
            unprocessed.add(it)
        }

        val views =
            resolver.getSymbolsWithAnnotation(Annotations.VIEW).filterIsInstance<KSClassDeclaration>().groupBy { it.validate() }

        views[false]?.forEach {
            unprocessed.add(it)
        }

        return unprocessed

    }


}


fun KSClassDeclaration.parseDatabase(): Boolean {
    TODO()
}