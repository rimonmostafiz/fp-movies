package edu.miu.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * @author Rimon Mostafiz
 */
@Slf4j
public class FileReader {
    public static Optional<String> readFileContent(String fileName) {
        String text = null;
        try {
            InputStream stream = FileReader.class.getResourceAsStream("/" + fileName);
            if (stream == null) return Optional.empty();
            URL resource = ClassLoader.getSystemClassLoader().getResource(fileName);
            assert resource != null;
            File file = new File(resource.toURI());
            text = readFile(file.getPath(), StandardCharsets.UTF_8);
        } catch (Exception ex) {
            log.debug("Something went wrong while reading file: {} {}", fileName, ex);
        }
        return Optional.ofNullable(text);
    }

    private static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
