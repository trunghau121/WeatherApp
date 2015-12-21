package com.example.hau.weatherapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HAU on 9/3/2015.
 */
public class OpenWeatherMap implements Parcelable {
    private Coord coord;
    private List<Weather> weather;
    private Main main;
    private Wind wind;
    private Clouds clouds;
    private Sys sys;
    private int id;
    private long dt;
    private String name;
    private String cod;


    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
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

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }


    public OpenWeatherMap(){

    }
    public OpenWeatherMap(Parcel parcel) {
        weather = new ArrayList<Weather>();
        setCoord((Coord) parcel.readParcelable(Coord.class.getClassLoader()));
        parcel.readTypedList(weather, Weather.CREATOR);
        setMain((Main) parcel.readParcelable(Main.class.getClassLoader()));
        setWind((Wind) parcel.readParcelable(Wind.class.getClassLoader()));
        setClouds((Clouds) parcel.readParcelable(Clouds.class.getClassLoader()));
        setSys((Sys) parcel.readParcelable(Sys.class.getClassLoader()));
        setId(parcel.readInt());
        setDt(parcel.readLong());
        setName(parcel.readString());
        setCod(parcel.readString());


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(getCoord(), flags);
        dest.writeTypedList(getWeather());
        dest.writeParcelable(getMain(), flags);
        dest.writeParcelable(getWind(), flags);
        dest.writeParcelable(getClouds(), flags);
        dest.writeParcelable(getSys(), flags);
        dest.writeInt(getId());
        dest.writeLong(getDt());
        dest.writeString(getName());
        dest.writeString(getCod());
    }
    public static final Parcelable.Creator<OpenWeatherMap> CREATOR=new Creator<OpenWeatherMap>() {
        @Override
        public OpenWeatherMap createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public OpenWeatherMap[] newArray(int size) {
            return new OpenWeatherMap[0];
        }
    };
}
