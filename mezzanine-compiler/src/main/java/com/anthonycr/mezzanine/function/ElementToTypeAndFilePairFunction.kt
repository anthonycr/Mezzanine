package com.anthonycr.mezzanine.function

import com.anthonycr.mezzanine.FileStream
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.toTypeName
import java.io.File
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

/**
 * Adds a slash to the beginning of a path string if one does not exist there.
 */
private fun prependSlashIfNecessary(path: String): String =
        "${(if (path.startsWith("/")) "" else "/")}$path"

/**
 * A mapping function that takes a stream of supported elements (methods) and maps them to their
 * enclosing interfaces and the files represented by the method annotations.
 */
class ElementToTypeAndFilePairFunction() : (KSAnnotated) -> Pair<KSClassDeclaration, String> {

    override fun invoke(p1: KSAnnotated): Pair<KSClassDeclaration, String> {

        require(p1 is KSClassDeclaration)

        val filePath = p1.annotations.first { it.annotationType.toTypeName() == FileStream::class.asTypeName() }
            .arguments.first().value

        return Pair(p1, filePath as String)
    }

}
