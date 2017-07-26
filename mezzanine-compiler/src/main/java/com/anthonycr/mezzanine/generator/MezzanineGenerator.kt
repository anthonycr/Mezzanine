package com.anthonycr.mezzanine.generator

import com.anthonycr.mezzanine.utils.FileGenUtils
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import io.reactivex.Completable
import io.reactivex.functions.Function
import javax.lang.model.element.Modifier

/**
 * The Java generator.
 *
 *
 * Created by anthonycr on 5/22/17.
 */
object MezzanineGenerator {

    private val PACKAGE_NAME = "com.anthonycr.mezzanine"
    private val CLASS_NAME = "MezzanineGenerator"

    /**
     * Generates the Mezzanine [TypeSpec]
     * from a list of [TypeSpec].

     * @return a valid mapping function.
     */
    fun generateMezzanineTypeSpec(): Function<List<TypeSpec>, TypeSpec> {
        return Function { typeSpecs ->
            val mezzanineTypeSpecBuilder = TypeSpec.classBuilder(CLASS_NAME)
                    .addModifiers(Modifier.PUBLIC)

            typeSpecs.forEach { mezzanineTypeSpecBuilder.addType(it) }

            return@Function mezzanineTypeSpecBuilder.build()
        }
    }

    /**
     * Generates a [JavaFile] from
     * a [TypeSpec], [.PACKAGE_NAME]
     * will be used as the package name.

     * @return a valid mapping function.
     */
    fun generateJavaFile(): Function<TypeSpec, JavaFile> {
        return Function { typeSpec -> JavaFile.builder(PACKAGE_NAME, typeSpec).build() }
    }

    /**
     * Mapping function that writes a
     * [JavaFile] to disk and emits
     * a [Completable] that completes
     * when the file has been written to disk.

     * @param filer the filer necessary to write to disk.
     * *
     * @return a valid mapping function.
     */
    fun writeFileToDisk(): Function<JavaFile, Completable> {
        return Function { javaFile ->
            FileGenUtils.writeToFile(javaFile)
            return@Function Completable.complete()
        }
    }

}
