package com.anthonycr.mezzanine

/**
 * An example of an interface that will
 * be implemented by Mezzanine.
 *
 * Created by anthonycr on 5/22/17.
 */
@FileStream("src/main/assets/test.json")
interface FileReader {

    fun readInTestJsonFile(): String

}
