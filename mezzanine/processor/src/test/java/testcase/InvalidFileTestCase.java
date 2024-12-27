package testcase;

import com.anthonycr.mezzanine.FileStream;

/**
 * A test case for a non-existent file.
 */
@FileStream("src/test/FakeTest.json")
public interface InvalidFileTestCase {

    String test();

}
