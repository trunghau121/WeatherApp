package com.example.hau.weatherapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by HAU on 9/3/2015.
 */
public class Main implements Parcelable{
    private float temp;
    private float pressure;
    private int humidity;
    private float temp_min;
    private float temp_max;

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public float getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(float temp_min) {
        this.temp_min = temp_min;
    }

    public float getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(float temp_max) {
        this.temp_max = temp_max;
    }

    public Main(Parcel parcel){
        setTemp(parcel.readFloat());
        setPressure(parcel.readFloat());
        setHumidity(parcel.readInt());
        setTemp_min(parcel.readFloat());
        setTemp_max(parcel.readFloat());
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(getTemp());
        dest.writeFloat(getPressure());
        dest.writeInt(getHumidity());
        dest.writeFloat(getTemp_min());
        dest.writeFloat(getTemp_max());
    }
    public static final Parcelable.Creator<Main> CREATOR=new Creator<Main>() {
        @Override
        public Main createFromParcel(Parcel source) {
            return new Main(source);
        }

        @Override
        public Main[] newArray(int size) {
            return new Main[0];
        }
    };
}
