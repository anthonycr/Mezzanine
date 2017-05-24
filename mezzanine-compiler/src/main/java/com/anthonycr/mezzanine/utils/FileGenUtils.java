package com.anthonycr.mezzanine.utils;

import com.squareup.javapoet.JavaFile;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.tools.JavaFileObject;

/**
 * Utils used to write a {@link JavaFile}
 * to a file.
 * <p>
 * Created by anthonycr on 5/22/17.
 */
public class FileGenUtils {
    private FileGenUtils() {
        throw new UnsupportedOperationException("This class is not instantiable");
    }

    /**
     * Writes a Java file to the file system after
     * deleting the previous copy.
     *
     * @param file  the file to write.
     * @param filer the Filer to use to do the writing.
     * @throws IOException throws an exception if we are unable
     *                     to write the file to the filesystem.
     */
    public static void writeToFile(@NotNull JavaFile file, @NotNull Filer filer) throws IOException {
        String fileName =
            file.packageName.isEmpty() ? file.typeSpec.name : file.packageName + '.' + file.typeSpec.name;
        List<Element> originatingElements = file.typeSpec.originatingElements;
        JavaFileObject filerSourceFile = filer.createSourceFile(fileName, originatingElements.toArray(
            new Element[originatingElements.size()]));
        filerSourceFile.delete();
        Writer writer = null;
        try {
            writer = filerSourceFile.openWriter();
            file.writeTo(writer);
        } catch (Exception e) {
            try {
                filerSourceFile.delete();
            } catch (Exception ignored) {}
            throw e;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ignored) {}
            }
        }
    }
}
