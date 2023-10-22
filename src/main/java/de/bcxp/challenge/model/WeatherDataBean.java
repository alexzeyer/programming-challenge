package de.bcxp.challenge.model;

import com.opencsv.bean.CsvBindByName;

public class WeatherDataBean extends DataBean {
    @CsvBindByName(column = "Day")
    public int Day;
    @CsvBindByName(column = "MxT")
    public int MaximalTemperature;
    @CsvBindByName(column = "MnT")
    public int MinimalTemperature;
}
