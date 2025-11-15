package com.anthonycr.mezzanine

import com.anthonycr.mezzanine.library.Test2Json
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class Test2JsonTest {

    @Test
    fun `assert contents`() {
        val test2Json = mezzanine<Test2Json>()

        assertThat(test2Json.readJson()).isEqualTo(
            """
            {
              "menu": {
                "id": "file",
                "value": "File",
                "popup": {
                  "menuitem": [
                    {
                      "value": "New",
                      "onclick": "CreateNewDoc()"
                    },
                    {
                      "value": "Open",
                      "onclick": "OpenDoc()"
                    },
                    {
                      "value": "Close",
                      "onclick": "CloseDoc()"
                    }
                  ]
                }
              }
            }

        """.trimIndent()
        )
    }
}
