package com.example.hau.weatherapp.utils;

import android.util.Log;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.net.URL;

import com.example.hau.weatherapp.model.autocomplete.ResultsAuto;

/**
 * Created by HAU on 9/7/2015.
 */
public class AutocompleteAPI {
    public static final String KEY = "&key=AIzaSyBiOuTew0KnW1atFixnvhuIKv9M8uiAqNs";
    public static final String TYPES = "&types=geocode";
    public static final String LANGUAGE = "&language=vi-VN";

    /**
     * Function get data form Autocomplete API google
     *
     * @param query
     * @return
     */
    public static ResultsAuto getAutocomplete(String query) {
        ResultsAuto resultsAuto = null;
        try {
            String format = query.replaceAll(" ", "%20");
            String u = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=" +
                    format + TYPES + LANGUAGE + KEY;
            URL url = new URL(u);
            InputStreamReader streamReader = new InputStreamReader(url.openStream(), "UTF-8");
            resultsAuto = new Gson().fromJson(streamReader, ResultsAuto.class);
        } catch (Exception ex) {
            Log.e("AutocompleteAPI", ex.getMessage());
        }
        return resultsAuto;

    }
}
