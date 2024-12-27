package com.anthonycr.mezzanine.function

import com.anthonycr.mezzanine.utils.MessagerUtils
import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.toClassName
import javax.lang.model.element.TypeElement

/**
 * A mapping function that generates the [TypeSpec] for the interface represented by the
 * [TypeElement] which returns the [String].
 */
object GenerateFileStreamTypeSpecFunction : (Pair<KSClassDeclaration, String>) -> TypeSpec {

    override fun invoke(p1: Pair<KSClassDeclaration, String>): TypeSpec {
        val path = p1.second

        val singleMethod = p1.first.getDeclaredFunctions().first()

        MessagerUtils.reportInfo(singleMethod.simpleName.asString())

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

        return TypeSpec
            .classBuilder(p1.first.simpleName.asString())
            .addSuperinterface(p1.first.toClassName())
            .addFunction(funSpec)
            .build()
    }

}
