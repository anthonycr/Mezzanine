package com.anthonycr.mezzanine.extensions

import org.assertj.core.api.Assertions
import org.junit.Test

/**
 * Unit tests for extensions.
 */
class FunctionExtensionsKtTest {


    @Test
    fun test_DoOnNext() {
        val listOne = listOf("one", "two", "three")

        val listTwo = mutableListOf<String>()

        val listThree = listOne.asSequence().doOnNext { listTwo.add(it) }.toList()

        Assertions.assertThat(listOne).isEqualTo(listTwo)
        Assertions.assertThat(listOne).isEqualTo(listThree)
    }
}