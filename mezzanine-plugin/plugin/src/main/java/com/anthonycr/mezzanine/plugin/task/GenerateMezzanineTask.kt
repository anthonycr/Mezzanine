package com.anthonycr.mezzanine.plugin.task

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.asTypeName
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
        val text =
            inputFiles.map { it.invariantSeparatorsPath.substring(projectPath.length + 1) to it.readText() }
        val file = FileSpec.builder("com.anthonycr.mezzanine", "MezzanineReader")
            .addFunction(
                FunSpec.builder("readFromMezzanine")
                    .returns(String::class.asTypeName())
                    .apply {
                        parameters.add(
                            ParameterSpec.builder("path", String::class.asTypeName()).build()
                        )
                    }
                    .addCode(
                        CodeBlock.builder()
                            .beginControlFlow("return when(path)")
                            .apply {
                                text.forEach { (path, text) ->
                                    beginControlFlow("%S ->", path)
                                        .addStatement("\"\"\"\n$text\n\"\"\".trimIndent()")
                                        .endControlFlow()
                                }
                            }
                            .beginControlFlow("else ->")
                            .addStatement("error(%S)", "Unsupported path")
                            .endControlFlow()
                            .endControlFlow()
                            .build()
                    )
                    .build()
            )
            .build().writeTo(outputFiles.get().asFile)
        logger.warn("Generated file: $file")
    }
}