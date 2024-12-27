package com.anthonycr.mezzanine.function

import com.squareup.kotlinpoet.TypeSpec

/**
 * Generates the Mezzanine [TypeSpec] from a list of [TypeSpec].
 */
object GenerateMezzanineTypeSpecFunction : (List<TypeSpec>) -> TypeSpec {

    private const val CLASS_NAME = "MezzanineGenerator"

    override fun invoke(typeSpecs: List<TypeSpec>): TypeSpec {

        val mezzanineTypeSpecBuilder = TypeSpec
                .classBuilder(CLASS_NAME)

        typeSpecs.forEach { mezzanineTypeSpecBuilder.addType(it) }

        return mezzanineTypeSpecBuilder.build()
    }

}