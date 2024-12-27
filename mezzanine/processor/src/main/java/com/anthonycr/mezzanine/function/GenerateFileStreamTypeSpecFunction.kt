package com.anthonycr.mezzanine.function

import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.toClassName

/**
 * Generates the [TypeSpec] for the interface represented by the [KSClassDeclaration] which returns
 * the [String].
 */
fun Pair<KSClassDeclaration, String>.asFileStreamImplementationTypeSpec(
): Pair<KSClassDeclaration, TypeSpec> {
    val (interfaceDeclaration, path) = this

    val singleMethod = interfaceDeclaration.getDeclaredFunctions().first()

    val funSpec = FunSpec.builder(singleMethod.simpleName.asString())
        .addModifiers(KModifier.OVERRIDE)
        .returns(String::class.asTypeName())
        .addCode(
            CodeBlock.builder()
                .addStatement(
                    "return com.anthonycr.mezzanine.readFromMezzanine(%S)", path
                ).build()
        )
        .build()

    return interfaceDeclaration to TypeSpec
        .classBuilder(PREFIX + interfaceDeclaration.simpleName.asString())
        .addSuperinterface(interfaceDeclaration.toClassName())
        .addFunction(funSpec)
        .build()
}

private const val PREFIX = "_Mezzanine_"