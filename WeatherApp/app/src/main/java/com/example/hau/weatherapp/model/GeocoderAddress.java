package com.example.hau.weatherapp.model;

import java.util.List;

/**
 * Created by HAU on 9/6/2015.
 */
public class GeocoderAddress {
    List<Results> results;

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }
}
