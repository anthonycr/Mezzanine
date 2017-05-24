package com.anthonycr.mezzanine.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic.Kind;

/**
 * Utils to communicate with the compiler.
 * <p>
 * Created by anthonycr on 5/22/17.
 */
public class MessagerUtils {

    @Nullable private static Messager messager;

    public static void initialize(@NotNull Messager messager) {
        MessagerUtils.messager = messager;
    }

    @NotNull
    private static Messager safeMessager() {
        Preconditions.checkNotNull(messager);

        return messager;
    }

    /**
     * Report an error to the compiler
     * at the specified element.
     *
     * @param element the offending element.
     * @param message the error message.
     */
    public static void reportError(@NotNull Element element, @NotNull String message) {
        safeMessager().printMessage(Kind.ERROR, message, element);
    }

    /**
     * Log info to the compiler to
     * provide context to the process.
     *
     * @param message the message to log.
     */
    public static void reportInfo(@NotNull String message) {
        safeMessager().printMessage(Kind.NOTE, message);
    }

}
