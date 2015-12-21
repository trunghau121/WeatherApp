package com.example.hau.weatherapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by HAU on 9/3/2015.
 */
public class Sys implements Parcelable {
    private int type;
    private int id;
    private String country;
    private long sunrise;
    private long sunset;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public Sys(Parcel parcel) {
        setType(parcel.readInt());
        setId(parcel.readInt());
        setCountry(parcel.readString());
        setSunrise(parcel.readLong());
        setSunset(parcel.readLong());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getType());
        dest.writeInt(getId());
        dest.writeString(getCountry());
        dest.writeLong(getSunrise());
        dest.writeLong(getSunset());
    }
    public static final Parcelable.Creator<Sys> CREATOR =new Creator<Sys>() {
        @Override
        public Sys createFromParcel(Parcel source) {
            return new Sys(source);
        }

        @Override
        public Sys[] newArray(int size) {
            return new Sys[0];
        }
    };
}
