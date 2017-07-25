package com.anthonycr.mezzanine

/**
 * An example of an interface that will
 * be implemented by Mezzanine.
 *
 * Created by anthonycr on 5/22/17.
 */
interface FileReader {

    @FileStream("sample/src/main/assets/test.json")
    fun readInTestJsonFile(): String

}
