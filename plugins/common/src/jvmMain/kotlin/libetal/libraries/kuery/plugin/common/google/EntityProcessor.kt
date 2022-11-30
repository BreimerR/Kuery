package libetal.libraries.kuery.plugin.common.google

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import libetal.libraries.kuery.annotations.common.entities.Table
import libetal.libraries.kuery.annotations.common.entities.View

abstract class EntityProcessor(val environment: SymbolProcessorEnvironment) : SymbolProcessor {

    protected val codeGenerator by lazy {
        environment.codeGenerator
    }

    protected val options by lazy {
        environment.options
    }
}