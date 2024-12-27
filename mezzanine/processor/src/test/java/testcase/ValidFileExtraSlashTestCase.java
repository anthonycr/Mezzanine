package testcase;

import com.anthonycr.mezzanine.FileStream;

/**
 * A test case for {@code Test.json}.
 */
@FileStream("/src/test/Test.json")
public interface ValidFileExtraSlashTestCase {

    String test();

}
