package com.anthonycr.mezzanine

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class MezzanineProcessorProvider : SymbolProcessorProvider {
    override fun create(
        environment: SymbolProcessorEnvironment
    ): SymbolProcessor = MezzanineProcessor(
        codeGenerator = environment.codeGenerator,
        logger = environment.logger,
        generateMezzanine = environment.options["com.anthonycr.mezzanine.generate_mezzanine"]?.toBooleanStrictOrNull()
            ?: run {
                environment.logger.error("Did you forget to apply the Mezzanine plugin?")
                false
            }
    )
}
