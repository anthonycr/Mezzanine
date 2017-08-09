package com.anthonycr.mezzanine

import com.anthonycr.mezzanine.utils.MessagerUtils
import com.google.testing.compile.CompilationRule
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito
import javax.annotation.processing.Messager
import javax.lang.model.util.Elements

/**
 * An abstract unit test that sets up mock utils.
 */
abstract class AbstractUnitTest {

    @JvmField
    @Rule
    val compilationRule = CompilationRule()

    protected lateinit var elements: Elements

    @Before
    fun _setUp() {
        MessagerUtils.messager = Mockito.mock(Messager::class.java)
        elements = compilationRule.elements
    }
}
