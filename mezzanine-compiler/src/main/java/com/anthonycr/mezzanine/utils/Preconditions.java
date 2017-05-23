package com.anthonycr.mezzanine.utils;

/**
 * Created by anthonycr on 5/22/17.
 */

public class Preconditions {

    public static void checkNotNull(Object o) {
        if (o == null) {
            throw new NullPointerException("Object must not be null");
        }
    }

    public static void checkCondition(boolean condition) {
        if (!condition) {
            throw new UnsupportedOperationException("Condition not met");
        }
    }

}
