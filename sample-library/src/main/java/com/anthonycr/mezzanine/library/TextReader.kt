package com.anthonycr.mezzanine.library

import com.anthonycr.mezzanine.FileStream

@FileStream("src/main/assets/test.txt")
interface TextReader {

    fun readTextFile(): String

}
