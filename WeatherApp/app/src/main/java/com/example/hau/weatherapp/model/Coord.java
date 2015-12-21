package com.example.hau.weatherapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by HAU on 9/3/2015.
 */
public class Coord implements Parcelable{
    private double lat;
    private double lon;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public Coord(Parcel parcel){
        setLat(parcel.readDouble());
        setLon(parcel.readDouble());
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
         dest.writeDouble(getLat());
         dest.writeDouble(getLon());
    }
    public static final Parcelable.Creator<Coord> CREATOR =new Creator<Coord>() {
        @Override
        public Coord createFromParcel(Parcel source) {
            return new Coord(source);
        }

        @Override
        public Coord[] newArray(int size) {
            return new Coord[0];
        }
    };
}
