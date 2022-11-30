package libetal.libraries.kuery.plugin.sqlite.google


// TODO: My Hilight Was Hex 214283

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import libetal.libraries.kuery.annotations.common.columns.Column
import libetal.libraries.kuery.annotations.common.columns.PrimaryKey

import libetal.libraries.kuery.annotations.common.entities.Table
import libetal.libraries.kuery.annotations.common.entities.View
import libetal.libraries.kuery.plugin.common.google.VoidVisitor

class FileVisitor(
    private val resolver: Resolver,
    logger: KSPLogger,
    private val codeGenerator: CodeGenerator,
    private val packageName: String,
    val options: Map<String, String>
) : VoidVisitor<(KSClassDeclaration?) -> Unit>(logger) {

    override fun visitFile(file: KSFile, data: (KSClassDeclaration?) -> Unit) {

        for (declaration in file.declarations) {
            when (declaration) {
                is KSClassDeclaration -> declaration.parse(packageName, data)
            }
        }

    }

    private fun KSClassDeclaration.parse(packageName: String, onFail: (KSClassDeclaration?) -> Unit) =
        parseTable(packageName, onFail) || parseView(onFail)

    @OptIn(KspExperimental::class)
    fun KSClassDeclaration.parseTable(packageName: String, onFail: (KSClassDeclaration?) -> Unit): Boolean {
        val annotations = getAnnotationsByType(Table::class).toList()


        if (annotations.isEmpty()) return false
        val tableAnnotation = annotations.lastOrNull() ?: return false
        val className = simpleName.asString()
        val tableName = tableAnnotation.name.ifBlank { null } ?: "${className.lowercase()}s"

        val dataClassName = simpleName.asString()
        val modelName = "${className}sEntity"

        val generatedFile by lazy {
            try {
                codeGenerator.createNewFile(
                    // https://kotlinlang.org/docs/ksp-incremental.html
                    dependencies = Dependencies(false, *resolver.getAllFiles().toList().toTypedArray()),
                    packageName = packageName,
                    fileName = modelName,
                    "kt"
                )
            } catch (e: Exception) {
                null
            }
        }

        val visitor = PropertiesVisitor(
            packageName,
            logger
        )

        val entityClass = EntityClass(logger)

        for (property in getAllProperties()) {

            val columnAnnotation = property.getAnnotationsByType(Column::class).toList().lastOrNull()
            val primaryKeyAnnotation = property.getAnnotationsByType(PrimaryKey::class).toList().lastOrNull()

            if (columnAnnotation != null || primaryKeyAnnotation != null) {
                val errorMessage = property.accept(visitor, entityClass)

                if (errorMessage != null) {

                    warn(errorMessage)

                    onFail(this)

                    return false
                }
            }

        }

        generatedFile?.writer()?.use {
            it.write(
                """|package $packageName
                   |
                   |import ${options["databaseObjectClass"]}.int
                   |import ${options["databaseObjectClass"]}.text
                   |import ${options["databaseObjectClass"]}.query
                   |import ${options["databaseObjectClass"]}.blob
                   |import ${options["databaseObjectClass"]}.boolean
                   |import ${options["databaseObjectClass"]}.char
                   |import ${options["databaseObjectClass"]}.string
                   |import ${options["databaseObjectClass"]}.long
                   |import ${qualifiedName?.asString()}
                   |import libetal.libraries.kuery.core.statements.INSERT
                   |import libetal.libraries.kuery.core.statements.extensions.INTO
                   |import libetal.libraries.kuery.core.statements.extensions.VALUES
                   |import libetal.libraries.kuery.sqlite.core.entities.IdEntity
                   |
                   |object $modelName : IdEntity<$dataClassName, ${entityClass.primaryKeyType}>() {
                   |${entityClass.propertiesString}
                   |    override fun toString() = "$tableName"
                   |}
                   |""".trimMargin()
            )
        }

        return true

    }

    @OptIn(KspExperimental::class)
    fun KSClassDeclaration.parseView(onFail: (KSClassDeclaration?) -> Unit): Boolean {
        val annotations = getAnnotationsByType(View::class).toList()
        if (annotations.isEmpty()) return false


        return true
    }

}
