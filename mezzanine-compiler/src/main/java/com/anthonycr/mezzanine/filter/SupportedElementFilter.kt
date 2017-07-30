package com.anthonycr.mezzanine.filter

import com.anthonycr.mezzanine.utils.MessagerUtils
import io.reactivex.functions.Predicate
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ExecutableElement

/**
 * Filters a list of elements for only
 * the elements supported by Mezzanine.
 * If unsupported elements are in the stream,
 * it will report a message to the
 * [MessagerUtils] so that the consumer
 * knows what they have done wrong.
 *
 * Created by anthonycr on 5/22/17.
 */
object SupportedElementFilter : Predicate<Element> {

    override fun test(classElement: Element): Boolean {

        if (classElement.kind != ElementKind.INTERFACE) {
            MessagerUtils.reportError(classElement, "Only interfaces are supported")
            return false
        }

        if (classElement.enclosedElements.size != 1) {
            MessagerUtils.reportError(classElement, "Only interfaces with 1 method are supported")
            return false
        }

        val methodElement = classElement.enclosedElements[0]

        if (methodElement !is ExecutableElement) {
            MessagerUtils.reportError(methodElement, "Interface must contain one method")
            return false
        }

        if (methodElement.returnType.toString() != String::class.java.name.replace('$', '.')) {
            MessagerUtils.reportError(methodElement, "Interface's single method must have a String return type")
            return false
        }

        return true
    }

}
