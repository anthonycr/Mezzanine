package testcase;

import com.anthonycr.mezzanine.FileStream;

/**
 * A test case for {@code Test.json}.
 */
@FileStream("mezzanine-compiler/src/test/Test.json")
public interface ValidFileTestCase {

    String test();

}
