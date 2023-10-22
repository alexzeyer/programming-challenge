package de.bcxp.challenge.service;

import de.bcxp.challenge.model.WeatherDataBean;
import de.bcxp.challenge.service.IWeatherService;
import de.bcxp.challenge.utilities.CSVFileReader;
import de.bcxp.challenge.utilities.IFileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;

public class WeatherService implements IWeatherService {
    private final IFileReader fileReader;

    public WeatherService(){
        // In einem größeren Projekt würde diese Datenquellen-Instanz(fileReader) durch ein Container in injected werden.
        this.fileReader = new CSVFileReader();
    }

    public Integer GetDayWithMinTemperatureSpread(String filePath) throws Exception {
        // Die Inputvalidierung habe ich bereits beim Annehmen des Webcalls gemacht, allerdings würde ich diese, je nach
        // Verwendung dieser Businessklasse erneut durchführen.
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("Parameter ´filePath´ is not set.");
        }

        List<WeatherDataBean> weatherDataList = GetWeatherDataList(filePath);

        if (weatherDataList == null || weatherDataList.isEmpty()) {
            throw new Exception("Es wurden keine Wetterdaten ermittelt.");
        }

        WeatherDataBean weatherData = GetMinimalTemperatureSpread(weatherDataList);

        return weatherData.Day;
    }

    public List GetWeatherDataList(String filePath) throws IOException, URISyntaxException {
        return fileReader.readFile(filePath, WeatherDataBean.class, ',');
    }

    public WeatherDataBean GetMinimalTemperatureSpread(List<WeatherDataBean> weatherDataList) {
        return weatherDataList.stream()
                .min(Comparator.comparingDouble(weatherData -> weatherData.MaximalTemperature - weatherData.MinimalTemperature))
                .orElse(null);
    }
}
