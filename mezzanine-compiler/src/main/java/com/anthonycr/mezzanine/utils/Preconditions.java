package com.anthonycr.mezzanine.utils;

import org.jetbrains.annotations.Nullable;

/**
 * A set of conditions utils.
 * <p>
 * Created by anthonycr on 5/22/17.
 */
public class Preconditions {

    /**
     * Asserts that a value is not null.
     *
     * @param o the value that will be asserted
     *          to not be null.
     */
    public static void checkNotNull(@Nullable Object o) {
        if (o == null) {
            throw new NullPointerException("Object must not be null");
        }
    }

    /**
     * Asserts that a condition is true.
     *
     * @param condition the condition to
     *                  assert as true.
     */
    public static void checkCondition(boolean condition) {
        if (!condition) {
            throw new UnsupportedOperationException("Condition not met");
        }
    }

}
