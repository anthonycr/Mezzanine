package com.anthonycr.mezzanine.function

import com.squareup.javapoet.TypeSpec
import io.reactivex.functions.Function
import javax.lang.model.element.Modifier

/**
 * Generates the Mezzanine [TypeSpec] from a list of [TypeSpec].
 */
object GenerateMezzanineTypeSpecFunction : Function<List<TypeSpec>, TypeSpec> {

    private const val CLASS_NAME = "MezzanineGenerator"

    override fun apply(typeSpecs: List<TypeSpec>): TypeSpec {
        val mezzanineTypeSpecBuilder = TypeSpec.classBuilder(CLASS_NAME)
                .addModifiers(Modifier.PUBLIC)

        typeSpecs.forEach { mezzanineTypeSpecBuilder.addType(it) }

        return mezzanineTypeSpecBuilder.build()
    }
}