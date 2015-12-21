package com.example.hau.weatherapp.model.sixteendayweatherforecast;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HAU on 9/9/2015.
 */
public class MainForecast implements Parcelable {
    private City city;
    private List<ListForecast> list;
    private String cod;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public List<ListForecast> getList() {
        return list;
    }

    public void setList(List<ListForecast> list) {
        this.list = list;
    }

    public MainForecast(){

    }
    public MainForecast(Parcel parcel) {
        list = new ArrayList<ListForecast>();
        setCity((City) parcel.readParcelable(City.class.getClassLoader()));
        parcel.readTypedList(list, ListForecast.CREATOR);
        setCod(parcel.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(getCity(), flags);
        dest.writeTypedList(getList());
        dest.writeString(getCod());
    }
    public static final Parcelable.Creator<MainForecast>  CREATOR=new Creator<MainForecast>() {
        @Override
        public MainForecast createFromParcel(Parcel source) {
            return new MainForecast(source);
        }

        @Override
        public MainForecast[] newArray(int size) {
            return new MainForecast[0];
        }
    };
}
