package com.example.hau.weatherapp.model.weatherinday;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.hau.weatherapp.model.Clouds;
import com.example.hau.weatherapp.model.Coord;
import com.example.hau.weatherapp.model.Main;
import com.example.hau.weatherapp.model.Sys;
import com.example.hau.weatherapp.model.Weather;
import com.example.hau.weatherapp.model.Wind;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HAU on 9/14/2015.
 */
public class ListWeatherInDay implements Parcelable {
    private long dt;
    private List<Weather> weather;
    private Main main;
    private Wind wind;
    private Clouds clouds;
    private String dt_txt;

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    public ListWeatherInDay(Parcel parcel){
        weather=new ArrayList<Weather>();
        setDt(parcel.readLong());
        parcel.readTypedList(weather, Weather.CREATOR);
        setMain((Main) parcel.readParcelable(Main.class.getClassLoader()));
        setWind((Wind) parcel.readParcelable(Wind.class.getClassLoader()));
        setClouds((Clouds) parcel.readParcelable(Clouds.class.getClassLoader()));
        setDt_txt(parcel.readString());
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(getDt());
        dest.writeTypedList(getWeather());
        dest.writeParcelable(getMain(), flags);
        dest.writeParcelable(getWind(), flags);
        dest.writeParcelable(getClouds(), flags);
        dest.writeString(getDt_txt());
    }
    public static final Parcelable.Creator<ListWeatherInDay> CREATOR =new Creator<ListWeatherInDay>() {
        @Override
        public ListWeatherInDay createFromParcel(Parcel source) {
            return new ListWeatherInDay(source);
        }

        @Override
        public ListWeatherInDay[] newArray(int size) {
            return new ListWeatherInDay[0];
        }
    };
}
