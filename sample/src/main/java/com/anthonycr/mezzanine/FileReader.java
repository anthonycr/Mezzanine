package com.anthonycr.mezzanine;

/**
 * An example of an interface that will
 * be implemented by Mezzanine.
 * <p>
 * Created by anthonycr on 5/22/17.
 */
public interface FileReader {

    @FileStream("sample/src/main/assets/test.json")
    String readInTestJsonFile();

}
