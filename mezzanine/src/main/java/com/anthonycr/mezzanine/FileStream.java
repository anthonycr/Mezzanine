package com.anthonycr.mezzanine;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by anthonycr on 5/22/17.
 */
@Target({ElementType.METHOD})
public @interface FileStream {

    @NotNull String value();

}
