package com.anthonycr.mezzanine

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class Test1JsonTest {

    @Test
    fun `assert contents`() {
        val test1Json = mezzanine<Test1Json>()

        assertThat(test1Json.readJson()).isEqualTo(
            """
            {
              "glossary": {
                "title": "example glossary",
                "GlossDiv": {
                  "title": "S",
                  "GlossList": {
                    "GlossEntry": {
                      "ID": "SGML",
                      "SortAs": "SGML",
                      "GlossTerm": "Standard Generalized Markup Language",
                      "Acronym": "SGML",
                      "Abbrev": "ISO 8879:1986",
                      "GlossDef": {
                        "para": "A meta-markup language, used to create markup languages such as DocBook.",
                        "GlossSeeAlso": [
                          "GML",
                          "XML"
                        ]
                      },
                      "GlossSee": "markup"
                    }
                  }
                }
              }
            }

        """.trimIndent()
        )
    }
}
