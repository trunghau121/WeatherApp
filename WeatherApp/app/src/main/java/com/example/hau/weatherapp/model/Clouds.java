package com.example.hau.weatherapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by HAU on 9/3/2015.
 */
public class Clouds implements Parcelable {
    private int all;

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public Clouds(Parcel parcel) {
        setAll(parcel.readInt());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getAll());

    }

    public static final Parcelable.Creator<Clouds> CREATOR = new Creator<Clouds>() {
        @Override
        public Clouds createFromParcel(Parcel source) {
            return new Clouds(source);
        }

        @Override
        public Clouds[] newArray(int size) {
            return new Clouds[0];
        }
    };
}
