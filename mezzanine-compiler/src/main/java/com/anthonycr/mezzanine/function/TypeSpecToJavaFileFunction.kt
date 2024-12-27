package com.anthonycr.mezzanine.function

import com.anthonycr.mezzanine.function.TypeSpecToJavaFileFunction.PACKAGE_NAME
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec

/**
 * Generates a [JavaFile] from a [TypeSpec], [PACKAGE_NAME] will be used as the package name.
 */
object TypeSpecToJavaFileFunction : (TypeSpec) -> FileSpec {

    private const val PACKAGE_NAME = "com.anthonycr.mezzanine"

    override fun invoke(typeSpec: TypeSpec): FileSpec =
            FileSpec.builder(PACKAGE_NAME, "Mezzanine").addType(typeSpec).build()

}