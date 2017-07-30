package com.anthonycr.mezzanine.function

import com.anthonycr.mezzanine.FileStream
import com.anthonycr.mezzanine.utils.MessagerUtils
import io.reactivex.functions.Function
import java.io.File
import java.nio.file.Paths
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

/**
 * A mapping function that takes a stream of
 * supported elements (methods) and maps them
 * to their enclosing interfaces and the files
 * represented by the method annotations.
 */
object ElementToTypeAndFilePairFunction : Function<Element, Pair<TypeElement, File>> {

    private fun prependSlashIfNecessary(path: String): String {
        return "${(if (path.startsWith("/")) "" else "/")}$path"
    }

    override fun apply(element: Element): Pair<TypeElement, File> {

        require(element is TypeElement)

        val filePath = element.getAnnotation(FileStream::class.java).value

        requireNotNull(filePath)

        val currentRelativePath = Paths.get("")

        val absoluteFilePath = "${currentRelativePath.toAbsolutePath()}${prependSlashIfNecessary(filePath)}"
        val file = File(absoluteFilePath)

        if (!file.exists()) {
            MessagerUtils.reportError(element, "File does not exist")
        }

        return Pair(element as TypeElement, file)
    }

}