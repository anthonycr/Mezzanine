package com.anthonycr.mezzanine.generator

import com.anthonycr.mezzanine.utils.FileGenUtils
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import io.reactivex.Completable
import io.reactivex.functions.Function
import org.apache.commons.lang3.StringEscapeUtils
import javax.annotation.processing.Filer
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement

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
     * A mapping function that generates the [TypeSpec]
     * for the interface represented by the [TypeElement]
     * which returns the [String].

     * @return a valid mapping function.
     */
    fun generateGeneratorTypeSpecs(): Function<Pair<TypeElement, String>, TypeSpec> {
        return Function { (typeElement, stringContent) ->

            val fileContents = StringEscapeUtils.escapeJava(stringContent)

            val singleMethod = typeElement.enclosedElements[0] as ExecutableElement

            val methodSpec = MethodSpec.methodBuilder(singleMethod.simpleName.toString())
                    .addAnnotation(Override::class.java)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(String::class.java)
                    .addCode("return \"$1L\";\n", fileContents)
                    .build()

            return@Function TypeSpec.classBuilder(typeElement.simpleName.toString())
                    .addModifiers(Modifier.PUBLIC)
                    .addModifiers(Modifier.STATIC)
                    .addSuperinterface(ClassName.get(typeElement))
                    .addMethod(methodSpec)
                    .build()
        }
    }

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
    fun writeFileToDisk(filer: Filer): Function<JavaFile, Completable> {
        return Function { javaFile ->
            FileGenUtils.writeToFile(javaFile, filer)
            return@Function Completable.complete()
        }
    }

}
