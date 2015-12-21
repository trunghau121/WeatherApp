package com.example.hau.weatherapp.model.weatherinday;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HAU on 9/14/2015.
 */
public class WeatherInDay implements Parcelable{
    private String cod;
    private List<ListWeatherInDay> list;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public List<ListWeatherInDay> getList() {
        return list;
    }

    public void setList(List<ListWeatherInDay> list) {
        this.list = list;
    }
    public  WeatherInDay(){

    }
    public WeatherInDay(Parcel parcel){
        setCod(parcel.readString());
        parcel.readTypedList(list,ListWeatherInDay.CREATOR);
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
           dest.writeString(getCod());
           dest.writeTypedList(list);
    }
    public static final Parcelable.Creator<WeatherInDay> CREATOR =new Creator<WeatherInDay>() {
        @Override
        public WeatherInDay createFromParcel(Parcel source) {
            return new WeatherInDay(source);
        }

        @Override
        public WeatherInDay[] newArray(int size) {
            return new WeatherInDay[0];
        }
    };
}
