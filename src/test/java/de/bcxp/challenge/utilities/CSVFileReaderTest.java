package de.bcxp.challenge.utilities;

import de.bcxp.challenge.model.WeatherDataBean;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CSVFileReaderTest {

    // Hier könnte die Testdatengenerierung und -bereinigung, soweit diese für mehrere Methoden verwendet wird, ausgelagert werden,
    // allerdings habe ich das hier nicht gemacht, da die Ersparnis an Code bzw. der Nutzen nicht so groß ist.

    @Test
    void ReadCSVFileTest() throws IOException, URISyntaxException {
        String filePath = "de/bcxp/challenge/utilities/test.csv";
        String fullPath = ClassLoader.getSystemResource(".").getFile() + filePath;
        try (FileWriter writer = new FileWriter(fullPath)) {
            writer.write("Day,MxT,MnT\n");
            writer.write("1,88,59\n");
            writer.write("2,79,63\n");
            writer.write("3,77,55\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List list = new CSVFileReader().readFile(filePath, WeatherDataBean.class, ',');
        assertTrue(list.size() == 3, "CSVFileReader should give back an list with 3 entries.");

        new File(fullPath).delete();
    }

    @Test
    void ReadCSVFileWithNoDataTest() throws IOException, URISyntaxException {
        String filePath = "de/bcxp/challenge/utilities/test.csv";
        String fullPath = ClassLoader.getSystemResource(".").getFile() + filePath;
        try (FileWriter writer = new FileWriter(fullPath)) {
            writer.write("");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List list = new CSVFileReader().readFile(filePath, WeatherDataBean.class, ',');
        assertTrue(list.isEmpty(), "CSVFileReader should give back an empty list.");

        new File(fullPath).delete();
    }

    @Test
    void ReadCSVFileWithNonExistingFileTest() {
        assertThrows(IOException.class, () -> {
            new CSVFileReader().readFile("", CSVFileReaderTest.class, ',');
        });
    }

}