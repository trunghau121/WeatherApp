package com.example.hau.weatherapp.model.sixteendayweatherforecast;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by HAU on 9/9/2015.
 */
public class TempForecast implements Parcelable {
    private float day;
    private float min;
    private float max;
    private float night;
    private float eve;
    private float morn;

    public float getDay() {
        return day;
    }

    public void setDay(float day) {
        this.day = day;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getNight() {
        return night;
    }

    public void setNight(float night) {
        this.night = night;
    }

    public float getEve() {
        return eve;
    }

    public void setEve(float eve) {
        this.eve = eve;
    }

    public float getMorn() {
        return morn;
    }

    public void setMorn(float morn) {
        this.morn = morn;
    }

    public TempForecast(Parcel parcel){
        setDay(parcel.readFloat());
        setMin(parcel.readFloat());
        setMax(parcel.readFloat());
        setNight(parcel.readFloat());
        setEve(parcel.readFloat());
        setMorn(parcel.readFloat());
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
          dest.writeFloat(getDay());
          dest.writeFloat(getMin());
          dest.writeFloat(getMax());
          dest.writeFloat(getNight());
          dest.writeFloat(getEve());
          dest.writeFloat(getMorn());
    }
    public static final Parcelable.Creator<TempForecast> CREATOR=new Creator<TempForecast>() {
        @Override
        public TempForecast createFromParcel(Parcel source) {
            return new TempForecast(source);
        }

        @Override
        public TempForecast[] newArray(int size) {
            return new TempForecast[0];
        }
    };
}
