package com.anthonycr.mezzanine;

import com.anthonycr.mezzanine.utils.Preconditions;
import com.google.common.base.Charsets;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import io.reactivex.functions.Function;

/**
 * Created by anthonycr on 5/22/17.
 */

public class MezzanineMapper {

    private static final class MezzanineEntry<T> implements Map.Entry<TypeElement, T> {

        private TypeElement key;
        private T value;

        public MezzanineEntry(TypeElement key, T value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public TypeElement getKey() {
            return key;
        }

        @Override
        public T getValue() {
            return value;
        }

        @Override
        public T setValue(T value) {
            return this.value = value;
        }
    }

    private static String prependSlashIfNecessary(@NotNull String path) {
        return (path.startsWith("/") ? "" : "/") + path;
    }

    @NotNull
    public static Function<Element, Map.Entry<TypeElement, File>> mapElementToFile() {
        return element -> {

            Element enclosingElement = element.getEnclosingElement();

            Preconditions.checkCondition(enclosingElement instanceof TypeElement);

            String filePath = element.getAnnotation(FileStream.class).value();

            Preconditions.checkNotNull(filePath);

            Path currentRelativePath = Paths.get("");

            String absoluteFilePath = currentRelativePath.toAbsolutePath() + prependSlashIfNecessary(filePath);
            File file = new File(absoluteFilePath);


            return new MezzanineEntry<>((TypeElement) enclosingElement, file);
        };
    }

    @NotNull
    public static Function<Map.Entry<TypeElement, File>, Map.Entry<TypeElement, String>> mapFileToString() {
        return typeElementFileEntry -> {

            String fileAsString = new String(Files.readAllBytes(typeElementFileEntry.getValue().toPath()), Charsets.UTF_8);

            return new MezzanineEntry<>(typeElementFileEntry.getKey(), fileAsString);
        };
    }
}
