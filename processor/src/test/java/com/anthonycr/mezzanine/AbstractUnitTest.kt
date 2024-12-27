package com.anthonycr.mezzanine

import com.google.testing.compile.CompilationRule
import org.junit.Before
import org.junit.Rule
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
        elements = compilationRule.elements
    }
}
