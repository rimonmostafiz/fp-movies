package edu.miu.utils;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rimon Mostafiz
 */
public class CSVFileReader {
    public static List<String[]> read(String fileName) {
        List<String[]> data = new ArrayList<>();
        try {
            InputStream resource = CSVFileReader.class.getResourceAsStream("/" + fileName);
            assert resource != null;
            //File file = new File(resource.toURI());
            try (CSVReader reader = new CSVReader(new InputStreamReader(resource))) {
                reader.forEach(data::add);
            }
        } catch (Exception ex) {
            System.out.printf("Something went wrong while reading file: %s\n", fileName);
            ex.printStackTrace(System.out);
        }
        return data;
    }
}
