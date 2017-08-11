package com.anthonycr.mezzanine.utils

import org.junit.Test
import org.mockito.Mockito
import javax.lang.model.element.Element

/**
 * Unit tests for [MessagerUtils].
 */
class MessagerUtilsTest {

    @Test(expected = IllegalArgumentException::class)
    fun test_ReportError_UninitializedMessager_ThrowsException() {
        val element = Mockito.mock(Element::class.java)
        MessagerUtils.reportError(element, "Test error")
    }

    @Test(expected = IllegalArgumentException::class)
    fun test_ReportInfo_UninitializedMessager_ThrowsException() {
        MessagerUtils.reportInfo("Test message")
    }
}