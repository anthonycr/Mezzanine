package com.anthonycr.mezzanine.function

import org.junit.Test
import org.mockito.Mockito
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement

/**
 * Additional unit tests for [GenerateFileStreamTypeSpecFunction].
 */
class GenerateFileStreamTypeSpecFunctionTest {

    @Test(expected = IndexOutOfBoundsException::class)
    fun `TypeElement with no enclosed elements fails`() {
        val typeElement = Mockito.mock(TypeElement::class.java)
        Mockito.`when`(typeElement.enclosedElements).thenReturn(mutableListOf())

        GenerateFileStreamTypeSpecFunction.invoke(Pair(typeElement, ""))
    }

    @Test(expected = ClassCastException::class)
    fun `TypeElement with non ExecutableElement fails`() {
        val typeElement = Mockito.mock(TypeElement::class.java)
        val field = Mockito.mock(VariableElement::class.java)
        Mockito.`when`(typeElement.enclosedElements).thenReturn(mutableListOf(field))

        GenerateFileStreamTypeSpecFunction.invoke(Pair(typeElement, ""))
    }

    @Test(expected = TypeCastException::class)
    fun `TypeElement with null enclosed element fails`() {
        val typeElement = Mockito.mock(TypeElement::class.java)
        Mockito.`when`(typeElement.enclosedElements).thenReturn(mutableListOf(null))

        GenerateFileStreamTypeSpecFunction.invoke(Pair(typeElement, ""))
    }
}
