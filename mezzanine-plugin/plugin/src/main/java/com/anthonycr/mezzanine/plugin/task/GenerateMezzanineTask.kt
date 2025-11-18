package com.anthonycr.mezzanine.plugin.task

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import org.apache.commons.text.StringEscapeUtils
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import kotlin.math.absoluteValue

@CacheableTask
abstract class GenerateMezzanineTask : DefaultTask() {

    @get:PathSensitive(PathSensitivity.RELATIVE)
    @get:InputFiles
    abstract val inputFiles: ConfigurableFileCollection

    @get:Input
    abstract val relativePath: Property<String>

    @get:OutputDirectory
    abstract val outputFiles: DirectoryProperty

    @TaskAction
    fun run() {
        val projectPath = relativePath.get()
        inputFiles.forEach { file ->
            val fileRelativePath = file.path.substring(projectPath.length + 1)
            val className = "_MezzanineReader_${fileRelativePath.hashCode().absoluteValue}"
            val text = file.readText()
            val escapedText = StringEscapeUtils.escapeJava(text).replace("$", "\\$")
            logger.warn(escapedText)
            val file = FileSpec.builder("com.anthonycr.mezzanine", className)
                .addType(
                    TypeSpec.objectBuilder(className)
                        .addFunction(
                            FunSpec.builder("readFromMezzanine")
                                .returns(String::class.asTypeName())
                                .addStatement("return \"%L\"", escapedText)
                                .build()
                        )
                        .build()
                )
                .build().writeTo(outputFiles.get().asFile)
            logger.warn("Generated file: $file")
        }
    }
}
