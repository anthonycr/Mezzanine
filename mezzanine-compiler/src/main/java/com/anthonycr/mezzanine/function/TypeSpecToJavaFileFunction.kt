package com.anthonycr.mezzanine.function

import com.anthonycr.mezzanine.function.TypeSpecToJavaFileFunction.PACKAGE_NAME
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec

/**
 * Generates a [JavaFile] from a [TypeSpec], [PACKAGE_NAME] will be used as the package name.
 */
object TypeSpecToJavaFileFunction : (TypeSpec) -> JavaFile {

    private const val PACKAGE_NAME = "com.anthonycr.mezzanine"

    override fun invoke(typeSpec: TypeSpec): JavaFile =
            JavaFile.builder(PACKAGE_NAME, typeSpec).build()

}