package com.anthonycr.mezzanine.map;

import com.anthonycr.mezzanine.FileStream;
import com.anthonycr.mezzanine.utils.MessagerUtils;
import com.anthonycr.mezzanine.utils.TypeEntry;
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
 * A set of mapping functions used to transform
 * a stream of supported elements into a usable
 * stream of components to build the file providers.
 * <p>
 * Created by anthonycr on 5/22/17.
 */
public final class MezzanineMapper {

    private MezzanineMapper() {}

    @NotNull
    private static String prependSlashIfNecessary(@NotNull String path) {
        return (path.startsWith("/") ? "" : "/") + path;
    }

    /**
     * A mapping function that takes a stream of
     * supported elements (methods) and maps them
     * to their enclosing interfaces and the files
     * represented by the method annotations.
     *
     * @return a valid mapping function.
     */
    @NotNull
    public static Function<Element, Map.Entry<TypeElement, File>> elementToTypeAndFilePair() {
        return element -> {

            Element enclosingElement = element.getEnclosingElement();

            Preconditions.checkCondition(enclosingElement instanceof TypeElement);

            String filePath = element.getAnnotation(FileStream.class).value();

            Preconditions.checkNotNull(filePath);

            Path currentRelativePath = Paths.get("");

            String absoluteFilePath = currentRelativePath.toAbsolutePath() + prependSlashIfNecessary(filePath);
            File file = new File(absoluteFilePath);

            if (!file.exists()) {
                MessagerUtils.reportError(element, "File does not exist");
            }

            return new TypeEntry<>((TypeElement) enclosingElement, file);
        };
    }

    /**
     * A mapping function that takes a stream of
     * interfaces and files and reads in the file,
     * emitting an stream of interfaces and file
     * contents (as strings).
     *
     * @return a valid mapping function.
     */
    @NotNull
    public static Function<Map.Entry<TypeElement, File>, Map.Entry<TypeElement, String>> fileToStringContents() {
        return typeElementFileEntry -> {

            String fileAsString = new String(Files.readAllBytes(typeElementFileEntry.getValue().toPath()), Charsets.UTF_8);

            return new TypeEntry<>(typeElementFileEntry.getKey(), fileAsString);
        };
    }
}
