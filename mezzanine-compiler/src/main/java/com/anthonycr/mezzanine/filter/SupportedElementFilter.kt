package com.anthonycr.mezzanine.filter

import com.anthonycr.mezzanine.utils.MessagerUtils
import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.Modifier
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.toTypeName

/**
 * Filters a list of elements for only the elements supported by Mezzanine. If unsupported elements
 * are in the stream, it will report a message to the [MessagerUtils] so that the consumer knows
 * what they have done wrong.
 */
object SupportedElementFilter : (KSAnnotated) -> Boolean {

    override fun invoke(p1: KSAnnotated): Boolean {

        if (p1 !is KSClassDeclaration) {
            MessagerUtils.reportError(p1, "Only interfaces are supported")
            return false
        }


        if (p1.getDeclaredFunctions().toList().size != 1) {
             MessagerUtils.reportError(p1, "Only interfaces with 1 method are supported")
            return false
        }

        val firstFunction = p1.getDeclaredFunctions().first()

        if (firstFunction.parameters.isNotEmpty()) {
            MessagerUtils.reportError(p1, "Functions cannot have parameters")
            return false
        }

        if (firstFunction.returnType?.toTypeName() != String::class.asTypeName()) {
            MessagerUtils.reportError(p1, "Function must have return type string")
            return false
        }

        return true
    }

}
