package com.anthonycr.mezzanine

import com.anthonycr.mezzanine.filter.isSupported
import com.anthonycr.mezzanine.function.asAggregatedMezzanineGeneratorFileSpec
import com.anthonycr.mezzanine.function.asDeclarationAndFilePath
import com.anthonycr.mezzanine.function.asFileStreamImplementationTypeSpec
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
        logger.info("Starting mezzanine processing")

        val symbolsWithAnnotation =
            resolver.getSymbolsWithAnnotation(FileStream::class.qualifiedName!!)

        val fileStreamTypeSpecs = symbolsWithAnnotation
            .filter { it.isSupported(logger) }
            .map { it.asDeclarationAndFilePath() }
            .onEach { (_, path) -> logger.info("Processing file: $path") }
            .map { it.asFileStreamImplementationTypeSpec() }
            .toList()

        if (fileStreamTypeSpecs.isNotEmpty()) {
            fileStreamTypeSpecs.asAggregatedMezzanineGeneratorFileSpec().forEach {
                it.writeTo(codeGenerator, true)
            }
        }

        return emptyList()
    }
}
