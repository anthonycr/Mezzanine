package com.anthonycr.mezzanine.utils

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSNode
import javax.annotation.processing.Messager
import javax.lang.model.element.Element
import javax.tools.Diagnostic.Kind

/**
 * Utils to communicate with the compiler.
 */
object MessagerUtils {

    lateinit var messager: KSPLogger

    /**
     * Report an error to the compiler at the specified node.
     *
     * @param node the offending node.
     * @param message the error message.
     */
    fun reportError(node: KSNode, message: String) = messager.error(message, node)

    /**
     * Log info to the compiler to provide context to the process.
     *
     * @param message the message to log.
     */
    fun reportInfo(message: String) = messager.warn(message)

}
