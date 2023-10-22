package de.bcxp.challenge.model;

import com.opencsv.bean.CsvBindByName;

public class CountryDataBean extends DataBean {
    @CsvBindByName(column = "Name")
    public String Name;
    @CsvBindByName(column = "Population", locale = "de-DE")
    public double Population;
    @CsvBindByName(column = "Area (km²)", locale = "de-DE")
    public double Area;
}
