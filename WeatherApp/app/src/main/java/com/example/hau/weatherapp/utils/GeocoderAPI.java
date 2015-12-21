package com.example.hau.weatherapp.utils;

import android.util.Log;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.net.URL;

import com.example.hau.weatherapp.model.GeocoderAddress;

/**
 * Created by HAU on 9/6/2015.
 */
public class GeocoderAPI {
    /**
     * Function get Address of decive with GPS
     *
     * @param lat
     * @param lon
     */

    public static GeocoderAddress getGeocoderAddress(double lat, double lon) {
        try {
            URL url = new URL("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lon + "&sensor=false&language=vi-VN");
            InputStreamReader inputStreamReader = new InputStreamReader(url.openStream(), "UTF-8");
            GeocoderAddress results = new Gson().fromJson(inputStreamReader, GeocoderAddress.class);
            return results;
        } catch (Exception ex) {
            Log.e("GeocoderAPI", ex.getMessage());
        }
        return null;
    }

    /**
     * Function get Address of decive with name city
     *
     * @param city
     */

    public static GeocoderAddress getGeocoderCity(String city) {
        try {
            String format = city.replaceAll(" ", "%20");
            URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + format + "&sensor=false&language=vi-VN");
            InputStreamReader inputStreamReader = new InputStreamReader(url.openStream(), "UTF-8");
            GeocoderAddress results = new Gson().fromJson(inputStreamReader, GeocoderAddress.class);
            return results;
        } catch (Exception ex) {
            Log.e("GeocoderCity", ex.getMessage());
        }
        return null;
    }
}
