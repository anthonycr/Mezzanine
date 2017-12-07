package com.anthonycr.mezzanine.function

import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier

/**
 * Generates the Mezzanine [TypeSpec] from a list of [TypeSpec].
 */
object GenerateMezzanineTypeSpecFunction : (List<TypeSpec>) -> TypeSpec {

    private const val CLASS_NAME = "MezzanineGenerator"

    override fun invoke(typeSpecs: List<TypeSpec>): TypeSpec {
        val mezzanineConstructorBuilder = MethodSpec
                .constructorBuilder()
                .addModifiers(Modifier.PRIVATE)

        val mezzanineTypeSpecBuilder = TypeSpec
                .classBuilder(CLASS_NAME)
                .addModifiers(Modifier.FINAL)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(mezzanineConstructorBuilder.build())

        typeSpecs.forEach { mezzanineTypeSpecBuilder.addType(it) }

        return mezzanineTypeSpecBuilder.build()
    }

}