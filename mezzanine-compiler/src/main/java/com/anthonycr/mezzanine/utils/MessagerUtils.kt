package com.anthonycr.mezzanine.utils

import javax.annotation.processing.Messager
import javax.lang.model.element.Element
import javax.tools.Diagnostic.Kind

/**
 * Utils to communicate with the compiler.
 */
object MessagerUtils {

    lateinit var messager: Messager

    /**
     * Report an error to the compiler at the specified element.
     *
     * @param element the offending element.
     *
     * @param message the error message.
     */
    fun reportError(element: Element, message: String) =
            messager.printMessage(Kind.ERROR, message, element)

    /**
     * Log info to the compiler to provide context to the process.
     *
     * @param message the message to log.
     */
    fun reportInfo(message: String) = println("Mezzanine: $message")

}
