package com.anthonycr.mezzanine.filter

import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.toTypeName

/**
 * Returns `true` if the [KSAnnotated] is supported by Mezzanine, `false` otherwise. Emits an error
 * to the [KSPLogger] if an unsupported type is detected.
 */
fun KSAnnotated.isSupported(kspLogger: KSPLogger): Boolean {
    if (this !is KSClassDeclaration) {
        kspLogger.error("Only interfaces are supported", this)
        return false
    }

    if (this.getDeclaredFunctions().toList().size != 1) {
        kspLogger.error("Only interfaces with 1 method are supported", this)
        return false
    }

    val firstFunction = this.getDeclaredFunctions().first()

    if (firstFunction.parameters.isNotEmpty()) {
        kspLogger.error("Functions cannot have parameters", this)
        return false
    }

    if (firstFunction.returnType?.toTypeName() != String::class.asTypeName()) {
        kspLogger.error("Function must have return type string", this)
        return false
    }

    return true
}
