package com.anthonycr.mezzanine;

/**
 * Created by anthonycr on 5/22/17.
 */
public interface FileReader {

    @FileStream("sample/src/main/assets/test.json")
    String readInTestJsonFile();

}
