package com.example.hau.weatherapp.Dialog;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.example.hau.weatherapp.Image.IconWeather;
import com.example.hau.weatherapp.R;
import com.example.hau.weatherapp.model.weatherinday.ListWeatherInDay;
import com.example.hau.weatherapp.utils.Time;

/**
 * Created by HAU on 9/23/2015.
 */
public class DialogInDay {
    private TextView txtWindInday, txtCloudlinessInday, txtPressureInday, txtHumidtyInday,
            txtTempInday, txtDescriptionInday,
            txtDateInday, txtTimeInday, txtDayInday;
    private ImageView ivInday;
    private Context mContext;

    public DialogInDay(Context mContext){
        this.mContext =mContext;

    }
    public void showDialog(ListWeatherInDay owp){
        MaterialDialog dialog =new MaterialDialog.Builder(mContext)
                .customView(R.layout.review_inday, true)
                .positiveText("THOÁT")
                .positiveColorRes(R.color.md_material_blue_800)
                .theme(Theme.LIGHT)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                    }
                }).build();
        createViewInDay(dialog);
        setDataInDay(owp);
        dialog.show();
    }
    public void createViewInDay(MaterialDialog dialog) {
        txtWindInday = (TextView) dialog.findViewById(R.id.txtWindspeedInday);
        txtCloudlinessInday = (TextView) dialog.findViewById(R.id.txtCloudInday);
        txtPressureInday = (TextView) dialog.findViewById(R.id.txtPressureInday);
        txtHumidtyInday = (TextView) dialog.findViewById(R.id.txtHumidityInday);
        ivInday = (ImageView) dialog.findViewById(R.id.ivInday);
        txtTempInday = (TextView) dialog.findViewById(R.id.txtTempInday);
        txtDateInday = (TextView) dialog.findViewById(R.id.txtDateInday);
        txtTimeInday = (TextView) dialog.findViewById(R.id.txtTimeInday);
        txtDayInday = (TextView) dialog.findViewById(R.id.txtDayInday);
        txtDescriptionInday = (TextView) dialog.findViewById(R.id.txtDescriptionInday);
    }

    public void setDataInDay(ListWeatherInDay owp) {
        String temperature = (int) (owp.getMain().getTemp() - 273.15) + "°C";
        String wind = owp.getWind().getSpeed() + " m/s";
        String cloudiness = owp.getClouds().getAll() + "%";

        String description = owp.getWeather().get(0).getDescription();

        String pressure = owp.getMain().getPressure() + " hpa";
        String humidity = owp.getMain().getHumidity() + "%";
        String date = Time.convertDate(owp.getDt());
        String dayofweek = Time.getDayofWeek(owp.getDt());
        String time = Time.convertTime(owp.getDt());

        String icon = owp.getWeather().get(0).getIcon();
        ivInday.setImageResource(IconWeather.getIconWeatherReview(icon));
        txtWindInday.setText(wind);
        txtCloudlinessInday.setText(cloudiness);
        txtPressureInday.setText(pressure);
        txtHumidtyInday.setText(humidity);
        txtDateInday.setText(date);
        txtTimeInday.setText(time);
        txtDayInday.setText(dayofweek);
        txtDescriptionInday.setText(description);
        txtTempInday.setText(temperature);
    }

}
