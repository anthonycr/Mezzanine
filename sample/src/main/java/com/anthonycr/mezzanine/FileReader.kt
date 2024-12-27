package com.anthonycr.mezzanine

/**
 * An example of an interface that will be implemented by Mezzanine.
 */
@FileStream("src/main/assets/test.json")
interface FileReader {

    fun readInTestJsonFile(): String

}
