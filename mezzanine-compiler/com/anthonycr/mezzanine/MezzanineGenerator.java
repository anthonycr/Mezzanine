package com.anthonycr.mezzanine;

import java.lang.Override;
import java.lang.String;

public final class MezzanineGenerator {
  private MezzanineGenerator() {
  }

  public static class ValidFileTestCase implements testcase.ValidFileTestCase {
    @Override
    public String test() {
      return "{\n  \"field\": \"value\"\n}";
    }
  }
}
