package de.bcxp.challenge.service;

import de.bcxp.challenge.model.CountryDataBean;
import de.bcxp.challenge.utilities.CSVFileReader;
import de.bcxp.challenge.utilities.IFileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;

public class CountryService implements ICountryService {
    private final IFileReader fileReader;

    public CountryService(){
        this.fileReader = new CSVFileReader();
    }

    public String GetCountryNameWithMaxPeoplePerSquareKilometer(String filePath) throws Exception {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("Parameter ´filePath´ is not set.");
        }

        List<CountryDataBean> countryDataList = GetCountryDataList(filePath);

        if (countryDataList == null || countryDataList.isEmpty()) {
            throw new Exception("Es wurden keine Countrydaten ermittelt.");
        }

        CountryDataBean countryData = GetMaxPeoplePerSquareKilometer(countryDataList);

        return countryData.Name;
    }

    public List GetCountryDataList(String filePath) throws IOException, URISyntaxException {
        return fileReader.readFile(filePath, CountryDataBean.class, ';');
    }

    public CountryDataBean GetMaxPeoplePerSquareKilometer(List<CountryDataBean> countryDataList) {
        return countryDataList.stream()
                .max(Comparator.comparingDouble(countryData -> countryData.Population / countryData.Area))
                .orElse(null);
    }
}
