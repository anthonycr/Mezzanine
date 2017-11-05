package com.anthonycr.mezzanine.utils

import com.squareup.javapoet.JavaFile
import java.io.IOException
import javax.annotation.processing.Filer

/**
 * Utils used to write a [JavaFile] to a file.
 */
object FileGenUtils {

    lateinit var filer: Filer

    /**
     * Writes a Java file to the file system after deleting the previous copy.
     *
     * @param file  the file to write.
     *
     * @throws IOException throws an exception if we are unable to write the file to the filesystem.
     */
    @Throws(IOException::class)
    fun writeToDisk(file: JavaFile) {
        require(file.packageName.isNotBlank())

        val fileName = "${file.packageName}.${file.typeSpec.name}"
        val originatingElements = file.typeSpec.originatingElements
        val filerSourceFile = filer.createSourceFile(fileName, *originatingElements.toTypedArray())

        filerSourceFile.delete()

        filerSourceFile.openWriter().use {
            file.writeTo(it)
        }
    }
}
