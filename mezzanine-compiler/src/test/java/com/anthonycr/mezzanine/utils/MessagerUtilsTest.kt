package com.anthonycr.mezzanine.utils

import org.junit.Test
import org.mockito.Mockito.mock
import javax.lang.model.element.Element

/**
 * Unit tests for [MessagerUtils].
 */
class MessagerUtilsTest {

    @Test(expected = UninitializedPropertyAccessException::class)
    internal fun `reportError fails when uninitialized`() {
        val element = mock(Element::class.java)
        MessagerUtils.reportError(element, "test error")
    }

    @Test(expected = UninitializedPropertyAccessException::class)
    internal fun `reportInfo fails when uninitialized`() {
        MessagerUtils.reportInfo("test message")
    }
}