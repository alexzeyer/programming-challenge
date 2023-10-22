package de.bcxp.challenge.service;

import de.bcxp.challenge.model.CountryDataBean;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CountryServiceTest {

    @Test
    void MinSpreadCalculationTest() {
        List<CountryDataBean> data = new ArrayList<>();
        data.add(new CountryDataBean(){{
            Name = "Germany";
            Area = 357592;
            Population = 83000000;
        }});
        data.add(new CountryDataBean(){{
            Name = "Austria";
            Area = 83871;
            Population = 8900000;
        }});
        data.add(new CountryDataBean(){{
            Name = "Switzerland";
            Area = 41285;
            Population = 8700000;
        }});

        String result = new CountryService().GetMaxPeoplePerSquareKilometer(data).Name;
        assertTrue(result.equals("Germany"), "GetMaxPeoplePerSquareKilometer calculation is invalid.");
    }

    // Integrations-Test vom Aufruf bis zur Datenquelle
    @Test
    void GetDayWithMinTemperatureSpreadTest() throws Exception {
        String filePath = "de/bcxp/challenge/test.csv";
        String fullPath = ClassLoader.getSystemResource(".").getFile() + filePath;
        try (FileWriter writer = new FileWriter(fullPath)) {
            writer.write(
                    "Name;Population;Area (km²);\n" +
                    "Austria;8900000;83871;\n" +
                    "Germany;83000000;357592;\n" +
                    "Switzerland;8700000;41285;\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String result = new CountryService().GetCountryNameWithMaxPeoplePerSquareKilometer(filePath);
        assertTrue(result.equals("Germany"), "GetCountryNameWithMaxPeoplePerSquareKilometer method is invalid.");

        new File(fullPath).delete();
    }

    @Test
    void GetDayWithMinTemperatureSpreadWithNoFileNameTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CountryService().GetCountryNameWithMaxPeoplePerSquareKilometer("");
        });
    }
}