package com.example.hau.weatherapp.model.sixteendayweatherforecast;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.hau.weatherapp.model.Coord;

/**
 * Created by HAU on 9/11/2015.
 */
public class City implements Parcelable{
    private Coord coord;
    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }
    public City(Parcel parcel){
        setCoord((Coord)parcel.readParcelable(Coord.class.getClassLoader()));

    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
         dest.writeParcelable(getCoord(),flags);
    }
    public static final Parcelable.Creator<City> CREATOR=new Creator<City>() {
        @Override
        public City createFromParcel(Parcel source) {
            return new City(source);
        }

        @Override
        public City[] newArray(int size) {
            return new City[0];
        }
    };
}
