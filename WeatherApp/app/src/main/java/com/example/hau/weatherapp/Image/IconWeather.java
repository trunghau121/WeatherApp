package com.example.hau.weatherapp.Image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.hau.weatherapp.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by HAU on 9/4/2015.
 */
public class IconWeather {


    public static Bitmap getBitmap(String idIcon) {
        Bitmap myBitmap = null;
        try {
            String urlIcon = "http://openweathermap.org/img/w/" + idIcon + ".png";
            URL urlConnection = new URL(urlIcon);
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getIconWeatherReview(String idIcon) {
        int icon = 0;
        switch (idIcon) {
            case "01d":
                icon = R.drawable.d01d;
                break;
            case "01n":
                icon = R.drawable.n01n;
                break;
            case "02d":
                icon = R.drawable.d02d;
                break;
            case "02n":
                icon = R.drawable.n02n;
                break;
            case "03d":
                icon = R.drawable.d03d;
                break;
            case "03n":
                icon = R.drawable.d03d;
                break;
            case "04d":
                icon = R.drawable.d04d;
                break;
            case "04n":
                icon = R.drawable.d04d;
                break;
            case "09d":
                icon = R.drawable.d09d;
                break;
            case "09n":
                icon = R.drawable.d09d;
                break;
            case "10d":
                icon = R.drawable.d10d;
                break;
            case "10n":
                icon = R.drawable.n10n;
                break;
            case "11d":
                icon = R.drawable.d11d;
                break;
            case "11n":
                icon = R.drawable.d11d;
                break;
            case "13d":
                icon = R.drawable.d13d;
                break;
            case "13n":
                icon = R.drawable.d13d;
                break;
            case "50d":
                icon = R.drawable.d50d;
                break;
            case "50n":
                icon = R.drawable.d50d;
                break;

        }
        return icon;
    }
}
