package com.example.hau.weatherapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by HAU on 9/3/2015.
 */
public class Wind implements Parcelable {
    private float speed;
    private float deg;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getDeg() {
        return deg;
    }

    public void setDeg(float deg) {
        this.deg = deg;
    }

    public Wind(Parcel parcel) {
        setSpeed(parcel.readFloat());
        setDeg(parcel.readFloat());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(getSpeed());
        dest.writeFloat(getDeg());
    }

    public static final Parcelable.Creator<Wind> CREATOR = new Creator<Wind>() {
        @Override
        public Wind createFromParcel(Parcel source) {
            return new Wind(source);
        }

        @Override
        public Wind[] newArray(int size) {
            return new Wind[0];
        }
    };
}
