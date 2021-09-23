package edu.miu.utils;

import com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Optional;

/**
 * @author Rimon Mostafiz
 */
public class FileReader {
    public static Optional<String> readFileContent(String fileName) {
        InputStream stream = FileReader.class.getResourceAsStream("/" + fileName);
        if (stream == null) return Optional.empty();
        String text = null;
        try (Reader reader = new InputStreamReader(stream)) {
            text = CharStreams.toString(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(text);
    }
}
