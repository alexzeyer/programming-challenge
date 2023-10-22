package de.bcxp.challenge.service;

import de.bcxp.challenge.model.WeatherDataBean;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WeatherServiceTest {

    @Test
    void MinSpreadCalculationTest() {
        List<WeatherDataBean> data = new ArrayList<>();
        data.add(new WeatherDataBean(){{
            Day = 1;
            MinimalTemperature = 20;
            MaximalTemperature = 30;
        }});
        data.add(new WeatherDataBean(){{
            Day = 2;
            MinimalTemperature = 20;
            MaximalTemperature = 35;
        }});
        data.add(new WeatherDataBean(){{
            Day = 3;
            MinimalTemperature = 18;
            MaximalTemperature = 30;
        }});

        Integer result = new WeatherService().GetMinimalTemperatureSpread(data).Day;
        assertTrue(result == 1, "GetMinimalTemperatureSpreadCalculation is invalid.");
    }

    // Integrations-Test vom Aufruf bis zur Datenquelle
    @Test
    void GetDayWithMinTemperatureSpreadTest() throws Exception {
        String filePath = "de/bcxp/challenge/test.csv";
        String fullPath = ClassLoader.getSystemResource(".").getFile() + filePath;
        try (FileWriter writer = new FileWriter(fullPath)) {
            writer.write("Day,MxT,MnT\n");
            writer.write("1,88,59\n");
            writer.write("2,79,63\n");
            writer.write("3,77,55\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Integer result = new WeatherService().GetDayWithMinTemperatureSpread(filePath);
        assertTrue(result == 2, "GetMinimalTemperatureSpreadCalculation is invalid.");

        new File(fullPath).delete();
    }

    @Test
    void GetDayWithMinTemperatureSpreadWithNoFileNameTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new WeatherService().GetDayWithMinTemperatureSpread("");
        });
    }
}