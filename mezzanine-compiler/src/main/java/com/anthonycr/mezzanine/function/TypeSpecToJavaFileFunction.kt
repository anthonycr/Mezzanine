package com.anthonycr.mezzanine.function

import com.anthonycr.mezzanine.function.TypeSpecToJavaFileFunction.PACKAGE_NAME
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import io.reactivex.functions.Function

/**
 * Generates a [JavaFile] from
 * a [TypeSpec], [PACKAGE_NAME]
 * will be used as the package name.
 */
object TypeSpecToJavaFileFunction : Function<TypeSpec, JavaFile> {

    private const val PACKAGE_NAME = "com.anthonycr.mezzanine"

    override fun apply(typeSpec: TypeSpec): JavaFile {
        return JavaFile.builder(PACKAGE_NAME, typeSpec).build()
    }

}