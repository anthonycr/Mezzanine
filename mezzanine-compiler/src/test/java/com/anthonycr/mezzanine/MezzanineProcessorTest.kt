package com.anthonycr.mezzanine

import org.assertj.core.api.Assertions.assertThat
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
        assertThat(processorTester.compile(ValidFileTestCase::class).isSuccessful()).isTrue()
    }

    @Test
    fun `ValidFileExtraSlashTestCase compilation succeeds`() {
        assertThat(processorTester.compile(ValidFileExtraSlashTestCase::class).isSuccessful()).isTrue()
    }

    @Test
    fun `InvalidFileTestCase compilation fails`() {
        assertThat(processorTester.compile(InvalidFileTestCase::class).isSuccessful()).isFalse()
    }

}
