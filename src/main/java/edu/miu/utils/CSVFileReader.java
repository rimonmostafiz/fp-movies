package edu.miu.utils;

import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rimon Mostafiz
 */
@Slf4j
public class CSVFileReader {
    public static List<String[]> read(String fileName) {
        List<String[]> data = new ArrayList<>();
        try {
            URL resource = ClassLoader.getSystemClassLoader().getResource(fileName);
            assert resource != null;
            File file = new File(resource.toURI());
            try (CSVReader reader = new CSVReader(new FileReader(file))) {
                reader.forEach(data::add);
            }
        } catch (Exception ex) {
            log.debug("Something went wrong while reading file: {} {}", fileName, ex);
        }
        return data;
    }
}
