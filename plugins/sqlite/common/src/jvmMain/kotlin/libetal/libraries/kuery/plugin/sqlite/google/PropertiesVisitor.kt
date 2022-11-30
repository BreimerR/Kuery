package libetal.libraries.kuery.plugin.sqlite.google

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.*
import libetal.libraries.kuery.annotations.common.columns.Column
import libetal.libraries.kuery.annotations.common.columns.PrimaryKey
import libetal.libraries.kuery.plugin.common.google.GeneralVisitor

class PropertiesVisitor(
    val packageName: String,
    logger: KSPLogger
) : GeneralVisitor<EntityClass, String?>(logger) {

    @OptIn(KspExperimental::class)
    override fun visitPropertyDeclaration(property: KSPropertyDeclaration, data: EntityClass): String? {
        val propertyName = property.simpleName.asString()


        val typeName = property.type.resolve().toString()
        val databaseType = when (typeName) {
            "Long" -> "long"
            "Int" -> "int"
            "String" -> "text"
            "Char" -> "char"
            else -> {
                property.warn("Unsupported Type $typeName")
                "string"
            }
        }

        val columnAnnotations = property.getAnnotationsByType(Column::class).toList()
        if (columnAnnotations.size > 1) property.warn("${property.qualifiedName?.asString()} Has been annotated with column more than once and only the last shall be considered")
        var columnName = columnAnnotations.lastOrNull()?.name?.ifBlank { null }

        val primaryKeyAnnotations = property.getAnnotationsByType(PrimaryKey::class).toList()
        if (primaryKeyAnnotations.size > 1) property.warn("Annotated with PrimaryKey more than once and only the last shall be utilized")
        val primaryKeyAnnotation = primaryKeyAnnotations.lastOrNull()
        val primaryKeyName = primaryKeyAnnotation?.name?.ifBlank { null }

        columnName = columnName ?: primaryKeyName ?: propertyName

        var propertyString = """val $propertyName =  $databaseType("$columnName")"""

        if (primaryKeyAnnotation != null) {
            data.primaryKeyType = typeName
            propertyString = """override val id = $databaseType("$columnName",  primary = true )""".trimMargin()
        }

        data.add(propertyString)

        return null
    }

    override fun visitPropertyGetter(getter: KSPropertyGetter, data: EntityClass): String? {
        return null
    }

    override fun visitPropertySetter(setter: KSPropertySetter, data: EntityClass): String? {
        return null
    }

    override fun visitReferenceElement(element: KSReferenceElement, data: EntityClass): String? {
        return null
    }

    override fun visitTypeAlias(typeAlias: KSTypeAlias, data: EntityClass): String? {
        return null
    }

    override fun visitTypeArgument(typeArgument: KSTypeArgument, data: EntityClass): String? {
        return null
    }

    override fun visitTypeParameter(typeParameter: KSTypeParameter, data: EntityClass): String? {
        return null
    }

    override fun visitTypeReference(typeReference: KSTypeReference, data: EntityClass): String? {
        return null
    }

    override fun visitValueArgument(valueArgument: KSValueArgument, data: EntityClass): String? {
        return null
    }

    override fun visitValueParameter(valueParameter: KSValueParameter, data: EntityClass): String? {
        return null
    }


    override fun visitAnnotated(annotated: KSAnnotated, data: EntityClass): String? {
        return null
    }

    override fun visitAnnotation(annotation: KSAnnotation, data: EntityClass): String? {
        return null
    }

    override fun visitCallableReference(reference: KSCallableReference, data: EntityClass): String? {
        return null
    }

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: EntityClass): String? {
        return null
    }

    override fun visitClassifierReference(reference: KSClassifierReference, data: EntityClass): String? {
        return null
    }

    override fun visitDeclaration(declaration: KSDeclaration, data: EntityClass): String? {
        return null
    }

    override fun visitDeclarationContainer(declarationContainer: KSDeclarationContainer, data: EntityClass): String? {
        return null
    }

    override fun visitDynamicReference(reference: KSDynamicReference, data: EntityClass): String? {
        return null
    }

    override fun visitFile(file: KSFile, data: EntityClass): String? {
        return null
    }

    override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: EntityClass): String? {
        return null
    }

    override fun visitModifierListOwner(modifierListOwner: KSModifierListOwner, data: EntityClass): String? {
        return null
    }

    override fun visitNode(node: KSNode, data: EntityClass): String? {
        return null
    }

    override fun visitParenthesizedReference(reference: KSParenthesizedReference, data: EntityClass): String? {
        return null
    }

    override fun visitPropertyAccessor(accessor: KSPropertyAccessor, data: EntityClass): String? {
        return null
    }

}