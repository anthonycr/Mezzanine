package com.anthonycr.mezzanine

import com.google.testing.compile.Compilation
import org.assertj.core.api.AbstractObjectAssert
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Condition
import org.junit.Test
import testcase.InvalidFileTestCase
import testcase.ValidFileExtraSlashTestCase
import testcase.ValidFileTestCase
import java.nio.file.Paths

/**
 * Unit tests for [MezzanineProcessor].
 */
class MezzanineProcessorTest {

    private val processorTester = ProcessorTester { MezzanineProcessor() }

    @Test
    fun `ValidFileTestCase compilation succeeds`() {
        assertThat(processorTester.compile(ValidFileTestCase::class)).isSuccessful()
    }

    @Test
    fun `ValidFileTestCase with custom argument compilation succeeds`() {
        assertThat(processorTester.compile(
                ValidFileTestCase::class,
                options = listOf("-Amezzanine.projectPath=${Paths.get("").toAbsolutePath()}")
        ))
    }


    @Test
    fun `ValidFileExtraSlashTestCase compilation succeeds`() {
        assertThat(processorTester.compile(ValidFileExtraSlashTestCase::class)).isSuccessful()
    }

    @Test
    fun `InvalidFileTestCase compilation fails`() {
        assertThat(processorTester.compile(InvalidFileTestCase::class)).isUnsuccessful()
    }

    @Test
    fun `verify deterministic compilation`() {
        val input = arrayOf(
                ValidFileTestCase::class,
                ValidFileExtraSlashTestCase::class
        )

        val compilationHash1 = processorTester.compile(*input).hashOutput()
        val compilationHash2 = processorTester.compile(*input).hashOutput()

        assertThat(compilationHash1).isEqualTo(compilationHash2)
    }

    /**
     * Return the hashed contents of each file output by the [Compilation] joined to a single
     * [String].
     */
    private fun Compilation.hashOutput() = generatedFiles()
            .joinToString { it.getCharContent(false).hashCode().toString() }

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
