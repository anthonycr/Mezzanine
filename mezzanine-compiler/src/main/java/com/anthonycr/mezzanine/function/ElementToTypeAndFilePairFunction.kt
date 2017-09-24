package com.anthonycr.mezzanine.function

import com.anthonycr.mezzanine.FileStream
import java.io.File
import java.nio.file.Paths
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
object ElementToTypeAndFilePairFunction : (Element) -> Pair<TypeElement, File> {

    override fun invoke(element: Element): Pair<TypeElement, File> {

        require(element is TypeElement)

        val filePath = element.getAnnotation(FileStream::class.java).value

        val currentRelativePath = Paths.get("")

        val absoluteFilePath = "${currentRelativePath.toAbsolutePath()}${prependSlashIfNecessary(filePath)}"
        val file = File(absoluteFilePath)

        return Pair(element as TypeElement, file)
    }

}