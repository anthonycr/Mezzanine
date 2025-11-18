package com.anthonycr.mezzanine.function

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.ksp.addOriginatingKSFile
import com.squareup.kotlinpoet.ksp.toClassName

/**
 * Generates the Mezzanine [TypeSpec] from a list of [TypeSpec].
 */
fun Sequence<KSClassDeclaration>.asAggregatedMezzanineGeneratorFileSpec(): FileSpec {
    val mezzanineInternalSpec = FileSpec.builder(PACKAGE_NAME, CLASS_NAME)
        .addFunction(
            FunSpec.builder("mezzanineInternal")
                .addTypeVariable(TypeVariableName("T"))
                .addParameter(
                    "clazz",
                    ClassName("java.lang", "Class").parameterizedBy(TypeVariableName("T"))
                )
                .returns(TypeVariableName("T"))
                .beginControlFlow("return when(clazz.canonicalName)")
                .apply {
                    forEach { implementationDeclaration ->
                        val interfaceName =
                            implementationDeclaration.superTypes.first().resolve().toClassName()
                        implementationDeclaration.containingFile?.let { addOriginatingKSFile(it) }

                        val className = interfaceName.canonicalName
                        addStatement("\"${className}\" -> clazz.cast(${implementationDeclaration.qualifiedName!!.asString()}())!!")
                    }
                    addStatement("else -> error(\"Unsupported type \$clazz\")")
                }
                .endControlFlow()
                .build()
        )
        .build()

    return mezzanineInternalSpec
}

fun List<Pair<KSClassDeclaration, TypeSpec>>.asFileSpecs(): List<FileSpec> =
    map { (_, implementationTypeSpec) ->
        FileSpec.builder(
            PACKAGE_NAME,
            implementationTypeSpec.name!!
        ).addType(implementationTypeSpec).build()
    }

private const val PACKAGE_NAME = "com.anthonycr.mezzanine"
private const val CLASS_NAME = "MezzanineInternal"
