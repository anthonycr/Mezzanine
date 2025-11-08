package com.anthonycr.mezzanine

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class FileReaderTest {

    @Test
    fun `assert contents`() {
        val fileReader = mezzanine<FileReader>()

        assertThat(fileReader.readInTestJsonFile()).isEqualTo(
            """
            {
              "title": "Person",
              "type": "object",
              "properties": {
                "firstName": {
                  "type": "string"
                },
                "lastName": {
                  "type": "string"
                },
                "age": {
                  "description": "Age in years",
                  "type": "integer",
                  "minimum": 0
                }
              },
              "required": [
                "firstName",
                "lastName"
              ]
            }
        """.trimIndent()
        )
    }
}
