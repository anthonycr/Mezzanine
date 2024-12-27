package com.anthonycr.mezzanine.function

import com.anthonycr.mezzanine.FileStream
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.toTypeName

/**
 * Converts the [KSAnnotated] into a [Pair] of the type as a [KSClassDeclaration] and the file path
 * from the [FileStream] annotation as a [String].
 */
fun KSAnnotated.asDeclarationAndFilePath(): Pair<KSClassDeclaration, String> {
    require(this is KSClassDeclaration)

    val filePath =
        annotations.first { it.annotationType.toTypeName() == FileStream::class.asTypeName() }
            .arguments.first().value

    return Pair(this, filePath as String)
}
