package de.bcxp.challenge;

import de.bcxp.challenge.service.ICountryService;
import de.bcxp.challenge.service.CountryService;
import de.bcxp.challenge.service.IWeatherService;
import de.bcxp.challenge.service.WeatherService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The entry class for your solution. This class is only aimed as starting point and not intended as baseline for your software
 * design. Read: create your own classes and packages as appropriate.
 */
public final class App {
    /*
    Meine Wahl für ein Logging-Framework fiel auf Log4j, da Log4j ein gutes Featureset(Config-XML, Loglevel) hat und sehr verbreitet ist.
    Durch den Bekanntheitsgrad existiert bei Fehlerfällen eine große Community, die helfen kann.
     */
    private static final Logger logger = LogManager.getLogger(App.class.getName());

    /**
     * This is the main entry method of your program.
     * @param args The CLI arguments passed
     */
    public static void main(String... args) {
        String weatherFilePath = "de/bcxp/challenge/weather.csv";
        String countryFilePath = "de/bcxp/challenge/countries.csv";

        // Nehmen wir an, dass die main Methode unser Webcall von aussen ist, dann würde ich an dieser Stelle
        // den Input loggen und validieren.

        logger.info("Input: weatherFilePath " + weatherFilePath);
        logger.info("Input: countryFilePath " + countryFilePath);

        try {
            if (weatherFilePath == null || weatherFilePath.isEmpty()) {
                throw new IllegalArgumentException("Input weatherFilePath is empty.");
            }

            if (countryFilePath == null || countryFilePath.isEmpty()) {
                throw new IllegalArgumentException("Input countryFilePath is empty.");
            }

            IWeatherService weatherService = new WeatherService();
            Integer dayWithSmallestTempSpread = weatherService.GetDayWithMinTemperatureSpread(weatherFilePath);
            System.out.printf("Day with smallest temperature spread: %s%n", dayWithSmallestTempSpread);

            ICountryService countryService = new CountryService();
            String countryWithHighestPopulationDensity = countryService.GetCountryNameWithMaxPeoplePerSquareKilometer(countryFilePath);
            System.out.printf("Country with highest population density: %s%n", countryWithHighestPopulationDensity);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
