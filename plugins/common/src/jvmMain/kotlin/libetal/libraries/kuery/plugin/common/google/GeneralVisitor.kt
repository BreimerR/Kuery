package libetal.libraries.kuery.plugin.common.google

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.*

abstract class GeneralVisitor<T, R>(val logger: KSPLogger) : KSVisitor<T, R> {

    infix fun KSNode.warn(string: String) {
        logger.warn(string, this)
    }

    infix fun KSNode.error(string: String) {
        logger.error(string, this)
    }

    infix fun KSNode.info(info: String) {
        logger.info(info, this)
    }

    open fun onExit(){}

}

