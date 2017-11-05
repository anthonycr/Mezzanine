package com.anthonycr.mezzanine.filter

import com.anthonycr.mezzanine.AbstractUnitTest
import org.assertj.core.api.Assertions
import org.junit.Test
import org.mockito.Mockito
import javax.lang.model.element.*

/**
 * Unit tests for [SupportedElementFilter].
 */
class SupportedElementFilterTest : AbstractUnitTest() {

    private val mockElement = Mockito.mock(Element::class.java)
    private val mockMethod = Mockito.mock(ExecutableElement::class.java)

    @Test
    fun test_VariedElementKind() {

        // Train & Verify
        mockMethod.mockReturns(String::class.java)
        ElementKind.values()
                .filter { it != ElementKind.INTERFACE }
                .forEach {
                    mockElement.mockReturns(
                            elementKind = it,
                            modifiers = arrayOf(Modifier.PUBLIC),
                            enclosedElements = arrayOf(mockMethod)
                    )

                    Assertions.assertThat(SupportedElementFilter.invoke(mockElement)).isFalse()
                }

        // Train
        mockElement.mockReturns(
                elementKind = ElementKind.INTERFACE,
                modifiers = arrayOf(Modifier.PUBLIC),
                enclosedElements = arrayOf(mockMethod)
        )

        // Verify
        Assertions.assertThat(SupportedElementFilter.invoke(mockElement)).isTrue()
    }

    @Test
    fun test_VariedModifiers() {

        // Train & Verify
        mockMethod.mockReturns(String::class.java)
        Modifier.values()
                .filter { it != Modifier.PUBLIC }
                .forEach {
                    mockElement.mockReturns(
                            elementKind = ElementKind.INTERFACE,
                            modifiers = arrayOf(it),
                            enclosedElements = arrayOf(mockMethod)
                    )

                    Assertions.assertThat(SupportedElementFilter.invoke(mockElement)).isFalse()
                }

        // Train
        mockElement.mockReturns(
                elementKind = ElementKind.INTERFACE,
                modifiers = arrayOf(Modifier.PUBLIC),
                enclosedElements = arrayOf(mockMethod)
        )

        // Verify
        Assertions.assertThat(SupportedElementFilter.invoke(mockElement)).isTrue()
    }

    @Test
    fun test_VariedNumberOfContainedElements() {
        mockMethod.mockReturns(String::class.java)

        // Train - 0 methods
        mockElement.mockReturns(
                elementKind = ElementKind.INTERFACE,
                modifiers = arrayOf(Modifier.PUBLIC),
                enclosedElements = arrayOf()
        )

        // Verify
        Assertions.assertThat(SupportedElementFilter.invoke(mockElement)).isFalse()

        // Train - 1 method
        mockElement.mockReturns(
                elementKind = ElementKind.INTERFACE,
                modifiers = arrayOf(Modifier.PUBLIC),
                enclosedElements = arrayOf(mockMethod)
        )

        // Verify
        Assertions.assertThat(SupportedElementFilter.invoke(mockElement)).isTrue()

        // Train - 2 methods
        mockElement.mockReturns(
                elementKind = ElementKind.INTERFACE,
                modifiers = arrayOf(Modifier.PUBLIC),
                enclosedElements = arrayOf(mockMethod, mockMethod)
        )

        // Verify
        Assertions.assertThat(SupportedElementFilter.invoke(mockElement)).isFalse()
    }

    @Test
    fun test_VariedEnclosedElementTypes() {
        // Mock
        val mockField = Mockito.mock(VariableElement::class.java)

        // Train
        mockElement.mockReturns(
                elementKind = ElementKind.INTERFACE,
                modifiers = arrayOf(Modifier.PUBLIC),
                enclosedElements = arrayOf(mockField)
        )

        // Verify
        Assertions.assertThat(SupportedElementFilter.invoke(mockElement)).isFalse()

        // Train
        mockMethod.mockReturns(String::class.java)
        mockElement.mockReturns(
                elementKind = ElementKind.INTERFACE,
                modifiers = arrayOf(Modifier.PUBLIC),
                enclosedElements = arrayOf(mockMethod)
        )

        // Verify
        Assertions.assertThat(SupportedElementFilter.invoke(mockElement)).isTrue()
    }

    @Test
    fun test_VariedReturnTypes() {
        // Train
        mockMethod.mockReturns(Any::class.java)
        mockElement.mockReturns(
                elementKind = ElementKind.INTERFACE,
                modifiers = arrayOf(Modifier.PUBLIC),
                enclosedElements = arrayOf(mockMethod)
        )

        // Verify
        Assertions.assertThat(SupportedElementFilter.invoke(mockElement)).isFalse()

        // Train
        mockMethod.mockReturns(String::class.java)
        mockElement.mockReturns(
                elementKind = ElementKind.INTERFACE,
                modifiers = arrayOf(Modifier.PUBLIC),
                enclosedElements = arrayOf(mockMethod)
        )

        // Verify
        Assertions.assertThat(SupportedElementFilter.invoke(mockElement)).isTrue()
    }

    private fun <T> ExecutableElement.mockReturns(clazz: Class<T>) {
        Mockito.`when`(this.returnType).thenReturn(elements.getTypeElement(clazz.name).asType())
    }

    private fun Element.mockReturns(elementKind: ElementKind,
                                    modifiers: Array<Modifier>,
                                    enclosedElements: Array<Element>) {
        Mockito.`when`(this.kind).thenReturn(elementKind)
        Mockito.`when`(this.modifiers).thenReturn(modifiers.toMutableSet())
        Mockito.`when`(this.enclosedElements).thenReturn(enclosedElements.toMutableList())
    }
}