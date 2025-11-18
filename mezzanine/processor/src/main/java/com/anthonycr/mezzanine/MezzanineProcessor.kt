package com.anthonycr.mezzanine

import com.anthonycr.mezzanine.filter.isSupported
import com.anthonycr.mezzanine.function.asDeclarationAndFilePath
import com.anthonycr.mezzanine.function.asFileSpecs
import com.anthonycr.mezzanine.function.asFileStreamImplementationTypeSpec
import com.anthonycr.mezzanine.function.asAggregatedMezzanineGeneratorFileSpec
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.ksp.writeTo

class MezzanineProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val generateMezzanine: Boolean
) : SymbolProcessor {

    private var hasGenerated: Boolean = false

    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        logger.info("Starting mezzanine processing")

        val symbolsWithAnnotation =
            resolver.getSymbolsWithAnnotation(FileStream::class.qualifiedName!!)

        val fileSpecs = symbolsWithAnnotation
            .filter { it.isSupported(logger) }
            .map { it.asDeclarationAndFilePath() }
            .onEach { (_, path) -> logger.info("Processing file: $path") }
            .map { it.asFileStreamImplementationTypeSpec() }
            .toList()
            .asFileSpecs()

        fileSpecs.forEach { it.writeTo(codeGenerator, true) }

        if (generateMezzanine && fileSpecs.isEmpty() && !hasGenerated) {
            hasGenerated = true
            resolver.getDeclarationsFromPackage("com.anthonycr.mezzanine")
                .filter { it.qualifiedName?.getShortName()?.startsWith("_Mezzanine_") == true }
                .filterIsInstance<KSClassDeclaration>()
                .asAggregatedMezzanineGeneratorFileSpec()
                .writeTo(codeGenerator, true)
        }

        return emptyList()
    }
}
