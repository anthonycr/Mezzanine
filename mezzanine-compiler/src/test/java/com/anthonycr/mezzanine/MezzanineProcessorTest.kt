package com.anthonycr.mezzanine

import com.google.testing.compile.Compilation
import org.assertj.core.api.AbstractObjectAssert
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Condition
import org.junit.Test
import testcase.InvalidFileTestCase
import testcase.ValidFileExtraSlashTestCase
import testcase.ValidFileTestCase

/**
 * Unit tests for [MezzanineProcessor].
 */
class MezzanineProcessorTest {

    private val processorTester = ProcessorTester({ MezzanineProcessor() })

    @Test
    fun `ValidFileTestCase compilation succeeds`() {
        assertThat(processorTester.compile(ValidFileTestCase::class)).isSuccessful()
    }

    @Test
    fun `ValidFileExtraSlashTestCase compilation succeeds`() {
        assertThat(processorTester.compile(ValidFileExtraSlashTestCase::class)).isSuccessful()
    }

    @Test
    fun `InvalidFileTestCase compilation fails`() {
        assertThat(processorTester.compile(InvalidFileTestCase::class)).isUnsuccessful()
    }

    /**
     * An extension that asserts that a compilation is unsuccessful.
     */
    private fun AbstractObjectAssert<*, Compilation>.isUnsuccessful() {
        has(object : Condition<Compilation>() {
            override fun matches(value: Compilation?) =
                    value?.status() == com.google.testing.compile.Compilation.Status.FAILURE
        })
    }

    /**
     * An extension that asserts that a compilation is successful.
     */
    private fun AbstractObjectAssert<*, Compilation>.isSuccessful() {
        has(object : Condition<Compilation>() {
            override fun matches(value: Compilation?) =
                    value?.status() == com.google.testing.compile.Compilation.Status.SUCCESS
        })
    }

}
