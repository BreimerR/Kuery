package libetal.libraries.kuery.plugin.common.google

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.*

abstract class VoidVisitor<T>(logger: KSPLogger) : GeneralVisitor<T, Unit>(logger) {

    override fun visitAnnotated(annotated: KSAnnotated, data: T) {
        logger.warn("${this::class.qualifiedName}.visitAnnotated: Not yet implemented")
    }

    override fun visitAnnotation(annotation: KSAnnotation, data: T) {
        logger.warn("${this::class.qualifiedName}.visitAnnotation: Not yet implemented")
    }

    override fun visitCallableReference(reference: KSCallableReference, data: T) {
        logger.warn("${this::class.qualifiedName}.visitCallableReference: Not yet implemented")
    }

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: T) {
        logger.warn("${this::class.qualifiedName}.visitClassDeclaration: Not yet implemented")
    }

    override fun visitClassifierReference(reference: KSClassifierReference, data: T) {
        logger.warn("${this::class.qualifiedName}.visitClassifierReference: Not yet implemented")
    }

    override fun visitDeclaration(declaration: KSDeclaration, data: T) {
        logger.warn("${this::class.qualifiedName}.visitDeclaration: Not yet implemented")
    }

    override fun visitDeclarationContainer(declarationContainer: KSDeclarationContainer, data: T) {
        logger.warn("${this::class.qualifiedName}.visitDeclarationContainer: Not yet implemented")
    }

    override fun visitDynamicReference(reference: KSDynamicReference, data: T) {
        logger.warn("${this::class.qualifiedName}.visitDynamicReference: Not yet implemented")
    }

    override fun visitFile(file: KSFile, data: T) {
        logger.warn("${this::class.qualifiedName}.visitFile: Not yet implemented")
    }

    override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: T) {
        logger.warn("${this::class.qualifiedName}.visitFunctionDeclaration: Not yet implemented")
    }

    override fun visitModifierListOwner(modifierListOwner: KSModifierListOwner, data: T) {
        logger.warn("${this::class.qualifiedName}.visitModifierListOwner: Not yet implemented")
    }

    override fun visitNode(node: KSNode, data: T) {
        logger.warn("${this::class.qualifiedName}.visitNode: Not yet implemented")
    }

    override fun visitParenthesizedReference(reference: KSParenthesizedReference, data: T) {
        logger.warn("${this::class.qualifiedName}.visitParenthesizedReference: Not yet implemented")
    }

    override fun visitPropertyAccessor(accessor: KSPropertyAccessor, data: T) {
        logger.warn("${this::class.qualifiedName}.Not yet implemented")
    }

    override fun visitPropertyDeclaration(property: KSPropertyDeclaration, data: T) {
        logger.warn("${this::class.qualifiedName}.visitPropertyDeclaration: Not yet implemented")
    }

    override fun visitPropertyGetter(getter: KSPropertyGetter, data: T) {
        logger.warn("${this::class.qualifiedName}.visitPropertyGetter: Not yet implemented")
    }

    override fun visitPropertySetter(setter: KSPropertySetter, data: T) {
        logger.warn("${this::class.qualifiedName}.visitPropertySetter: Not yet implemented")
    }

    override fun visitReferenceElement(element: KSReferenceElement, data: T) {
        logger.warn("${this::class.qualifiedName}.visitReferenceElement: Not yet implemented")
    }

    override fun visitTypeAlias(typeAlias: KSTypeAlias, data: T) {
        logger.warn("${this::class.qualifiedName}.visitTypeAlias: Not yet implemented")
    }

    override fun visitTypeArgument(typeArgument: KSTypeArgument, data: T) {
        logger.warn("${this::class.qualifiedName}.visitTypeArgument: Not yet implemented")
    }

    override fun visitTypeParameter(typeParameter: KSTypeParameter, data: T) {
        logger.warn("${this::class.qualifiedName}.visitTypeParameter: Not yet implemented")
    }

    override fun visitTypeReference(typeReference: KSTypeReference, data: T) {
        logger.warn("${this::class.qualifiedName}.visitTypeReference: Not yet implemented")
    }

    override fun visitValueArgument(valueArgument: KSValueArgument, data: T) {
        logger.warn("visitValueArgument: Not yet implemented")
    }

    override fun visitValueParameter(valueParameter: KSValueParameter, data: T) {
        logger.warn("${this::class.qualifiedName}.visitValueParameter: Not yet implemented")
    }

}