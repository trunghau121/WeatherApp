package com.example.hau.weatherapp.model.sixteendayweatherforecast;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.hau.weatherapp.model.Weather;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HAU on 9/9/2015.
 */
public class ListForecast implements Parcelable {
    private long dt;
    private TempForecast temp;
    private  float pressure;
    private  float humidity;
    private List<Weather> weather;
    private float speed;
    private float deg;
    private float clouds;
    private float rain;

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public TempForecast getTemp() {
        return temp;
    }

    public void setTemp(TempForecast temp) {
        this.temp = temp;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

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

    public float getClouds() {
        return clouds;
    }

    public void setClouds(float clouds) {
        this.clouds = clouds;
    }

    public float getRain() {
        return rain;
    }

    public void setRain(float rain) {
        this.rain = rain;
    }

    public ListForecast(Parcel parcel){
        weather=new ArrayList<Weather>();
        setDt(parcel.readLong());
        setTemp((TempForecast) parcel.readParcelable(TempForecast.class.getClassLoader()));
        setPressure(parcel.readFloat());
        setHumidity(parcel.readFloat());
        parcel.readTypedList(weather,Weather.CREATOR);
        setSpeed(parcel.readFloat());
        setDeg(parcel.readFloat());
        setClouds(parcel.readFloat());
        setRain(parcel.readFloat());

    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(getDt());
        dest.writeParcelable(getTemp(), flags);
        dest.writeFloat(getPressure());
        dest.writeFloat(getHumidity());
        dest.writeTypedList(getWeather());
        dest.writeFloat(getSpeed());
        dest.writeFloat(getDeg());
        dest.writeFloat(getClouds());
        dest.writeFloat(getRain());
    }
    public  static final Parcelable.Creator<ListForecast> CREATOR=new Creator<ListForecast>() {
        @Override
        public ListForecast createFromParcel(Parcel source) {
            return new ListForecast(source);
        }

        @Override
        public ListForecast[] newArray(int size) {
            return new ListForecast[0];
        }
    };
}
