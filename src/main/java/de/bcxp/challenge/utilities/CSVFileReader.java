package de.bcxp.challenge.utilities;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CSVFileReader implements IFileReader {
    /*
    Meine Wahl für ein Data-To-Model Mapper fiel auf OpenCSV, da man mithilfe der Annotations in der WeatherDataBean
    ziemlich einfach direkt die CSV Daten in die Bean mappen kann.
     */

    public List readFile(String filePath, Class targetClass, char separator)  throws IOException, URISyntaxException {
        Path path = Paths.get(ClassLoader.getSystemResource(filePath).toURI());

        Reader reader = Files.newBufferedReader(path);
        CsvToBean cb = new CsvToBeanBuilder<Class>(reader)
                .withSeparator(separator)
                .withType(targetClass)
                .build();
        return cb.parse();
    }

}