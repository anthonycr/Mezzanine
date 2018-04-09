package com.anthonycr.mezzanine

import com.anthonycr.mezzanine.extensions.doOnNext
import com.anthonycr.mezzanine.filter.NonExistentFileFilter
import com.anthonycr.mezzanine.filter.SupportedElementFilter
import com.anthonycr.mezzanine.function.*
import com.anthonycr.mezzanine.options.OPTION_PROJECT_PATH
import com.anthonycr.mezzanine.source.MezzanineElementSource
import com.anthonycr.mezzanine.utils.FileGenUtils
import com.anthonycr.mezzanine.utils.MessagerUtils
import com.google.auto.service.AutoService
import java.nio.file.Paths
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
class MezzanineProcessor : AbstractProcessor() {

    private var isProcessed = false

    override fun getSupportedAnnotationTypes() = mutableSetOf(FileStream::class.java.name)

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()

    override fun getSupportedOptions() = mutableSetOf(OPTION_PROJECT_PATH)

    override fun init(processingEnvironment: javax.annotation.processing.ProcessingEnvironment) {
        super.init(processingEnvironment)
        MessagerUtils.messager = processingEnvironment.messager
        FileGenUtils.filer = processingEnvironment.filer
    }

    override fun process(set: Set<TypeElement>, roundEnvironment: RoundEnvironment): Boolean {
        if (isProcessed) {
            return true
        }

        isProcessed = true

        val projectRoot = processingEnv.options[OPTION_PROJECT_PATH] ?: Paths.get("").toAbsolutePath().toString()

        MessagerUtils.reportInfo("Starting processing")

        MezzanineElementSource(roundEnvironment)
                .createElementStream()
                .filter(SupportedElementFilter)
                .map(ElementToTypeAndFilePairFunction(projectRoot))
                .filter(NonExistentFileFilter)
                .doOnNext { typeElementFileEntry ->
                    MessagerUtils.reportInfo("Processing file: ${typeElementFileEntry.second}")
                }
                .map(FileToStringContentsFunction)
                .map(GenerateFileStreamTypeSpecFunction)
                .toList()
                .run(GenerateMezzanineTypeSpecFunction)
                .run(TypeSpecToJavaFileFunction)
                .run { FileGenUtils.writeToDisk(this) }
                .run { MessagerUtils.reportInfo("File successfully processed") }

        return true
    }
}
