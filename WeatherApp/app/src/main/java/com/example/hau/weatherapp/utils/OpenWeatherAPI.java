package com.example.hau.weatherapp.utils;

import android.util.Log;

import com.example.hau.weatherapp.model.sixteendayweatherforecast.MainForecast;
import com.example.hau.weatherapp.model.weatherinday.WeatherInDay;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.example.hau.weatherapp.model.OpenWeatherMap;

/**
 * Created by HAU on 9/3/2015.
 */
public class OpenWeatherAPI {
    /**
     * Function get weather of decive with GPS
     *
     * @param lat
     * @param lon
     */

    public static OpenWeatherMap getCurrentWeather(double lat, double lon) {
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&lang=vi");
            InputStreamReader inputStreamReader = new InputStreamReader(url.openStream(), "UTF-8");
            OpenWeatherMap results = new Gson().fromJson(inputStreamReader, OpenWeatherMap.class);
            return results;
        } catch (Exception ex) {
            Log.e("CurrentWeather", ex.getMessage());
        }
        return null;
    }

    /**
     * get weather form location
     *
     * @param city
     * @return
     */
    public static OpenWeatherMap searchLocationtWeather(String city) {
        try {
            String format = city.replaceAll(" ", "%20");
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + format + "&lang=vi");
            InputStreamReader inputStreamReader = new InputStreamReader(url.openStream(), "UTF-8");
            OpenWeatherMap results = new Gson().fromJson(inputStreamReader, OpenWeatherMap.class);
            return results;
        } catch (Exception ex) {
            Log.e("LocationtWeather", ex.getMessage());
        }
        return null;
    }

    /**
     * Function get
     * @param city
     * @param cnt
     * @return
     */
    public static MainForecast searchSixteenWeather(String city,String cnt){
        try {
            String format = city.replaceAll(" ", "%20");
            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q="+format+"&mode=json&units=metric&cnt="+cnt+"&lang=vi");
            InputStreamReader inputStreamReader = new InputStreamReader(url.openStream(), "UTF-8");
            MainForecast results = new Gson().fromJson(inputStreamReader, MainForecast.class);
            return results;
        } catch (Exception ex) {
            Log.e("SixteenWeather", ex.getMessage());
        }
        return null;
    }

    /**
     * get forecast weather in day form name city
     * @param city
     * @return WeatherInDay
     */
    public static WeatherInDay getWeatherInDay(String city) {
        try {
            String format = city.replaceAll(" ", "%20");
            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?q="+format+"&cnt=8&mode=json&lang=vi");
            InputStreamReader inputStreamReader = new InputStreamReader(url.openStream(), "UTF-8");
            WeatherInDay results = new Gson().fromJson(inputStreamReader, WeatherInDay.class);
            return results;
        } catch (Exception ex) {
            Log.e("WeatherInDay", ex.getMessage());
        }
        return null;
    }
    /**
     * get forecast weather in day form location
     * @param lat
     * @param lon
     * @return WeatherInDay
     */
    public static WeatherInDay getWeatherInDayLocation(double lat, double lon) {
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?lat="+lat+"&lon="+lon+"&cnt=8&mode=json&lang=vi");
            InputStreamReader inputStreamReader = new InputStreamReader(url.openStream(), "UTF-8");
            WeatherInDay results = new Gson().fromJson(inputStreamReader, WeatherInDay.class);
            return results;
        } catch (Exception ex) {
            Log.e("WeatherInDayLocation", ex.getMessage());
        }
        return null;
    }
    /**
     * get forecast weather in day form name city
     * @param city
     * @return WeatherInDay
     */
    public static WeatherInDay getWeatherInDayCity(String city) {
        try {
            String format = city.replaceAll(" ", "%20");
            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?q="+format+"&cnt=8&mode=json&lang=vi");
            InputStreamReader inputStreamReader = new InputStreamReader(url.openStream(), "UTF-8");
            WeatherInDay results = new Gson().fromJson(inputStreamReader, WeatherInDay.class);
            return results;
        } catch (Exception ex) {
            Log.e("WeatherInDayLocation", ex.getMessage());
        }
        return null;
    }
}