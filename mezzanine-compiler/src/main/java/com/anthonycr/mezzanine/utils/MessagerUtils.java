package com.anthonycr.mezzanine.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic.Kind;

/**
 * Created by anthonycr on 5/22/17.
 */

public class MessagerUtils {

    @Nullable private static Messager messager;

    public static void intitialize(@NotNull Messager messager) {
        MessagerUtils.messager = messager;
    }

    @NotNull
    private static Messager safeMessager() {
        Preconditions.checkNotNull(messager);

        return messager;
    }

    public static void reportError(@NotNull Element element, @NotNull String message) {
        safeMessager().printMessage(Kind.ERROR, message, element);
    }

    public static void reportInfo(@NotNull String message) {
        safeMessager().printMessage(Kind.NOTE, message);
    }

}
