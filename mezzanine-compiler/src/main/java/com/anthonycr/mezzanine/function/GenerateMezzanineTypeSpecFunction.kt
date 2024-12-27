package com.anthonycr.mezzanine.function

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec

/**
 * Generates the Mezzanine [TypeSpec] from a list of [TypeSpec].
 */
fun List<TypeSpec>.asAggregatedMezzanineGeneratorFileSpec(): FileSpec {
    val mezzanineTypeSpecBuilder = TypeSpec.classBuilder(CLASS_NAME)

    forEach { mezzanineTypeSpecBuilder.addType(it) }

    return FileSpec.builder(PACKAGE_NAME, CLASS_NAME)
        .addType(mezzanineTypeSpecBuilder.build())
        .build()
}

private const val PACKAGE_NAME = "com.anthonycr.mezzanine"
private const val CLASS_NAME = "MezzanineGenerator"