package com.anthonycr.mezzanine.function

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import org.apache.commons.lang3.StringEscapeUtils
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement

/**
 * A mapping function that generates the [TypeSpec] for the interface represented by the
 * [TypeElement] which returns the [String].
 */
object GenerateFileStreamTypeSpecFunction : (Pair<TypeElement, String>) -> TypeSpec {

    override fun invoke(fileStreamPair: Pair<TypeElement, String>): TypeSpec {
        val fileContents = StringEscapeUtils.escapeJava(fileStreamPair.second)

        val singleMethod = fileStreamPair.first.enclosedElements[0] as ExecutableElement

        val methodSpec = MethodSpec
                .methodBuilder(singleMethod.simpleName.toString())
                .addAnnotation(Override::class.java)
                .addModifiers(Modifier.PUBLIC)
                .returns(String::class.java)
                .addCode("return \"$1L\";\n", fileContents)
                .build()

        return TypeSpec
                .classBuilder(fileStreamPair.first.simpleName.toString())
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC)
                .addSuperinterface(ClassName.get(fileStreamPair.first))
                .addMethod(methodSpec)
                .build()
    }

}
