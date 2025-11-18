package com.anthonycr.mezzanine.library

import com.anthonycr.mezzanine.FileStream

@FileStream("src/main/assets/test2.json")
interface Test2Json {

    fun readJson(): String

}
