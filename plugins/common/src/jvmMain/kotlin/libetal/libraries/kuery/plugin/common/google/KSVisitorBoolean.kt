package libetal.libraries.kuery.plugin.common.google

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.*

abstract class KSVisitorBoolean<T>(logger: KSPLogger) : GeneralVisitor<T, Boolean>(logger) {

    override fun visitAnnotated(annotated: KSAnnotated, data: T): Boolean {
        return true
    }

    override fun visitAnnotation(annotation: KSAnnotation, data: T): Boolean {
        return true
    }

    override fun visitCallableReference(reference: KSCallableReference, data: T): Boolean {
        return true
    }

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: T): Boolean {
        return true
    }

    override fun visitClassifierReference(reference: KSClassifierReference, data: T): Boolean {
        return true
    }

    override fun visitDeclaration(declaration: KSDeclaration, data: T): Boolean {
        return true
    }

    override fun visitDeclarationContainer(declarationContainer: KSDeclarationContainer, data: T): Boolean {
        return true
    }

    override fun visitDynamicReference(reference: KSDynamicReference, data: T): Boolean {
        return true
    }

    override fun visitFile(file: KSFile, data: T): Boolean {
        return true
    }

    override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: T): Boolean {
        return true
    }

    override fun visitModifierListOwner(modifierListOwner: KSModifierListOwner, data: T): Boolean {
        return true
    }

    override fun visitNode(node: KSNode, data: T): Boolean {
        return true
    }

    override fun visitParenthesizedReference(reference: KSParenthesizedReference, data: T): Boolean {
        return true
    }

    override fun visitPropertyAccessor(accessor: KSPropertyAccessor, data: T): Boolean {
        return true
    }

    override fun visitPropertyDeclaration(property: KSPropertyDeclaration, data: T): Boolean {
        return true
    }

    override fun visitPropertyGetter(getter: KSPropertyGetter, data: T): Boolean {
        return true
    }

    override fun visitPropertySetter(setter: KSPropertySetter, data: T): Boolean {
        return true
    }

    override fun visitReferenceElement(element: KSReferenceElement, data: T): Boolean {
        return true
    }

    override fun visitTypeAlias(typeAlias: KSTypeAlias, data: T): Boolean {
        return true
    }

    override fun visitTypeArgument(typeArgument: KSTypeArgument, data: T): Boolean {
        return true
    }

    override fun visitTypeParameter(typeParameter: KSTypeParameter, data: T): Boolean {
        return true
    }

    override fun visitTypeReference(typeReference: KSTypeReference, data: T): Boolean {
        return true
    }

    override fun visitValueArgument(valueArgument: KSValueArgument, data: T): Boolean {
        return true
    }

    override fun visitValueParameter(valueParameter: KSValueParameter, data: T): Boolean {
        return true
    }

}
