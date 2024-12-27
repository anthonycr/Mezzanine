package com.anthonycr.mezzanine

import com.anthonycr.mezzanine.extensions.doOnNext
import com.anthonycr.mezzanine.filter.SupportedElementFilter
import com.anthonycr.mezzanine.function.ElementToTypeAndFilePairFunction
import com.anthonycr.mezzanine.function.GenerateFileStreamTypeSpecFunction
import com.anthonycr.mezzanine.function.GenerateMezzanineTypeSpecFunction
import com.anthonycr.mezzanine.function.TypeSpecToJavaFileFunction
import com.anthonycr.mezzanine.utils.MessagerUtils
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.squareup.kotlinpoet.ksp.writeTo

class MezzanineProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        MessagerUtils.messager = logger

        MessagerUtils.reportInfo("Starting processing")

        val symbolsWithAnnotation =
            resolver.getSymbolsWithAnnotation(FileStream::class.qualifiedName!!)

        val fileStreamTypeSpecs = symbolsWithAnnotation
            .filter(SupportedElementFilter)
            .map(ElementToTypeAndFilePairFunction())
            .doOnNext { typeElementFileEntry ->
                MessagerUtils.reportInfo("Processing file: ${typeElementFileEntry.second}")
            }
            .map { it.first to it.second }
            .map(GenerateFileStreamTypeSpecFunction)
            .toList()

        if (fileStreamTypeSpecs.isNotEmpty()) {
            fileStreamTypeSpecs.run(GenerateMezzanineTypeSpecFunction)
                .run(TypeSpecToJavaFileFunction)
                .writeTo(codeGenerator, true)
        }

        return emptyList()
    }
}
