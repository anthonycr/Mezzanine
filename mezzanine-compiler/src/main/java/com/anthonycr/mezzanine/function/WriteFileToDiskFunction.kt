package com.anthonycr.mezzanine.function

import com.anthonycr.mezzanine.utils.FileGenUtils
import com.squareup.javapoet.JavaFile
import io.reactivex.Completable
import io.reactivex.functions.Function

/**
 * Mapping function that writes a [JavaFile] to disk and emits a [Completable] that completes when
 * the file has been written to disk.
 */
object WriteFileToDiskFunction : Function<JavaFile, Completable> {

    override fun apply(javaFile: JavaFile): Completable {
        FileGenUtils.writeToFile(javaFile)

        return Completable.complete()
    }

}