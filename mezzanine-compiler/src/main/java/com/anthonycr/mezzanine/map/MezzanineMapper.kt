package com.anthonycr.mezzanine.map

import com.anthonycr.mezzanine.FileStream
import com.anthonycr.mezzanine.utils.MessagerUtils
import com.google.common.base.Charsets

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

import io.reactivex.functions.Function

/**
 * A set of mapping functions used to transform
 * a stream of supported elements into a usable
 * stream of components to build the file providers.
 *
 *
 * Created by anthonycr on 5/22/17.
 */
object MezzanineMapper {

    private fun prependSlashIfNecessary(path: String): String {
        return (if (path.startsWith("/")) "" else "/") + path
    }

    /**
     * A mapping function that takes a stream of
     * supported elements (methods) and maps them
     * to their enclosing interfaces and the files
     * represented by the method annotations.

     * @return a valid mapping function.
     */
    fun elementToTypeAndFilePair(): Function<Element, Pair<TypeElement, File>> {
        return Function { element ->

            val enclosingElement = element.enclosingElement

            require(enclosingElement is TypeElement)

            val filePath = element.getAnnotation(FileStream::class.java).value

            requireNotNull(filePath)

            val currentRelativePath = Paths.get("")

            val absoluteFilePath = "${currentRelativePath.toAbsolutePath()}${prependSlashIfNecessary(filePath)}"
            val file = File(absoluteFilePath)

            if (!file.exists()) {
                MessagerUtils.reportError(element, "File does not exist")
            }

            return@Function Pair(enclosingElement as TypeElement, file)
        }
    }

    /**
     * A mapping function that takes a stream of
     * interfaces and files and reads in the file,
     * emitting an stream of interfaces and file
     * contents (as strings).

     * @return a valid mapping function.
     */
    fun fileToStringContents(): Function<Pair<TypeElement, File>, Pair<TypeElement, String>> {
        return Function { (first, second) ->

            val fileAsString = String(Files.readAllBytes(second.toPath()), Charsets.UTF_8)

            return@Function Pair(first, fileAsString)
        }
    }
}
