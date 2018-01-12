package com.anthonycr.mezzanine.function

import org.junit.Test
import org.mockito.Mockito
import javax.lang.model.element.ExecutableElement

/**
 * Additional unit tests for [ElementToTypeAndFilePairFunction].
 */
class ElementToTypeAndFilePairFunctionTest {

    @Test(expected = Exception::class)
    fun test_NonTypeElement_ThrowsException() {
        val executableElement = Mockito.mock(ExecutableElement::class.java)

        ElementToTypeAndFilePairFunction("").invoke(executableElement)
    }

}