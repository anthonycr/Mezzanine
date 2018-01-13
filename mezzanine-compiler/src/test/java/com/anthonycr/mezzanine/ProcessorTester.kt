package com.anthonycr.mezzanine

import com.google.testing.compile.Compilation
import com.google.testing.compile.Compiler
import java.io.File
import javax.annotation.processing.Processor
import javax.tools.DiagnosticCollector
import javax.tools.ToolProvider
import kotlin.reflect.KClass

/**
 * A class that aids in compiling classes using a [Processor].
 */
class ProcessorTester(private val processor: () -> Processor) {

    private val classpathRoot = File(Thread.currentThread().contextClassLoader.getResource("").path)
    private val projectRoot: File = classpathRoot.parentFile.parentFile.parentFile.parentFile
    private val javaRoot = File(File(File(projectRoot, "src"), "test"), "java")
    private val javaFileManager = ToolProvider.getSystemJavaCompiler().getStandardFileManager(DiagnosticCollector(), null, null)

    /**
     * Compiles the [KClass] with the provided [processor].
     */
    fun compile(vararg kClass: KClass<*>, options: List<String> = listOf()): Compilation =
            Compiler.javac()
                    .withProcessors(processor())
                    .withOptions(options)
                    .compile(javaFileManager.getJavaFileObjects(
                            *kClass.map { it.asResourcePath().asClasspathFile() }.toTypedArray()
                    ))

    private fun String.asClasspathFile(): File {
        val ind = this.indexOf('$')

        return if (ind < 0) {
            File(javaRoot, this)
        } else {
            File(javaRoot, "${this.substring(0, ind)}.java")
        }
    }
}

private fun KClass<*>.asResourcePath(): String = "${this.java.name.replace('.', '/')}.java"
