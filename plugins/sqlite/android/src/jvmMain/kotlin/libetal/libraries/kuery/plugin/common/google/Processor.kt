package libetal.libraries.kuery.plugin.common.google

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSNode

abstract class Processor(environment: SymbolProcessorEnvironment) : SymbolProcessor {

    val codeGenerator by lazy {
        environment.codeGenerator
    }

    val options by lazy {
        environment.options
    }

    companion object {

        lateinit var logger: KSPLogger

        @Suppress("unused")
        fun logging(message: String, symbol: KSNode? = null) = logger.logging(message, symbol)

        @Suppress("unused")
        fun info(message: String, symbol: KSNode? = null) = logger.info(message, symbol)

        @Suppress("unused")
        fun warn(message: String, symbol: KSNode? = null) = logger.warn(message, symbol)

        fun error(message: String, symbol: KSNode? = null) = logger.error(message, symbol)

        @Suppress("unused")
        fun error(message: Any?, symbol: KSNode? = null) = error(message?.toString() ?: "Can't Resolve error message", symbol)

    }
}