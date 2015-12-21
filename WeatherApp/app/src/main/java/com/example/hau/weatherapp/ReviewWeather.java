package com.example.hau.weatherapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hau.weatherapp.Image.IconWeather;
import com.example.hau.weatherapp.fragments.Weatherweek;
import com.example.hau.weatherapp.gps.GPSTracker;
import com.example.hau.weatherapp.model.sixteendayweatherforecast.ListForecast;
import com.example.hau.weatherapp.utils.Time;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by HAU on 9/11/2015.
 */
public class ReviewWeather extends AppCompatActivity {
    private Toolbar tbReview;
    private TextView txtWindreview, txtCloudlinessreview, txtPressurereview, txtHumidtyreview,
            txtTempreview, txtDescriptionreview,
            txtDatereview, txtDayreview, txtTempdayreview, txtTempnight, txtLocationreviewweek;
    private ImageView ivReviewweek;
    private NumberFormat format = new DecimalFormat("#0.0");
    private String address = "";
    private GPSTracker gpsTracker = null;
    private ListForecast forecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rewiew_weather_week);
        Intent it = getIntent();
        createView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        if (savedInstanceState != null) {
            forecast = (ListForecast) savedInstanceState.getParcelable("datareview");
            address = savedInstanceState.getString("addressreview");
        } else {
            if (getIntent() != null && getIntent().getExtras() != null && getIntent().getBundleExtra("dataweek").getParcelable("ListForecast") != null) {
                forecast = (ListForecast) getIntent().getBundleExtra("dataweek").getParcelable("ListForecast");
                address = Weatherweek.positionCity;
            } else {
                Toast.makeText(this, "Fail!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        addActionView(forecast);
    }

    public void createView() {
        tbReview = (Toolbar) findViewById(R.id.tb_reviewweather);
        txtWindreview = (TextView) findViewById(R.id.txtWindspeedreview);
        txtCloudlinessreview = (TextView) findViewById(R.id.txtCloudreview);
        txtPressurereview = (TextView) findViewById(R.id.txtPressurereview);
        txtHumidtyreview = (TextView) findViewById(R.id.txtHumidityreview);
        txtTempreview = (TextView) findViewById(R.id.txtTempreview);
        txtDatereview = (TextView) findViewById(R.id.txtDatereview);
        txtDayreview = (TextView) findViewById(R.id.txtDayreview);
        txtTempnight = (TextView) findViewById(R.id.txtTempnightreview);
        txtTempdayreview = (TextView) findViewById(R.id.txtTempdayreview);
        txtLocationreviewweek = (TextView) findViewById(R.id.txtLocationreviewweek);
        txtDescriptionreview = (TextView) findViewById(R.id.txtDescriptionreview);
        ivReviewweek = (ImageView) findViewById(R.id.ivReviewweek);
        setSupportActionBar(tbReview);

    }

    public void addActionView(ListForecast listForecast) {
        txtLocationreviewweek.setText(address);
        tbReview.setTitle("Ứng dụng thời tiết");
        tbReview.setLogo(R.drawable.brightness);
        String datereview = Time.convertDate(listForecast.getDt());
        String timereview = Time.convertTime(listForecast.getDt());
        String dayreview = Time.getDayofWeek(listForecast.getDt());
        String tempreview = (int) ((listForecast.getTemp().getMin() + listForecast.getTemp().getMax()) / 2) + "°C";
        String descriptionreview = listForecast.getWeather().get(0).getDescription();
        String tempmin = (int) listForecast.getTemp().getMin() + "°C";
        String tempmax = (int) listForecast.getTemp().getMax() + "°C";
        String tempmorn = (int) listForecast.getTemp().getMorn() + "°C";
        String tempeven = (int) listForecast.getTemp().getEve() + "°C";
        String tempday = (int) listForecast.getTemp().getDay() + "°C";
        String tempnight = (int) listForecast.getTemp().getNight() + "°C";
        String wind = listForecast.getSpeed() + " m/s";
        String cloudiness = listForecast.getClouds() + "%";
        String pressure = listForecast.getPressure() + " hpa";
        String humidity = listForecast.getHumidity() + "%";
        String icon = listForecast.getWeather().get(0).getIcon();
        txtDatereview.setText(datereview);
        txtDayreview.setText(dayreview);
        txtTempreview.setText(tempreview);
        txtDescriptionreview.setText(descriptionreview);
        txtTempdayreview.setText(tempday);
        txtTempnight.setText(tempnight);
        txtWindreview.setText(wind);
        txtCloudlinessreview.setText(cloudiness);
        txtPressurereview.setText(pressure);
        txtHumidtyreview.setText(humidity);
        ivReviewweek.setImageResource(IconWeather.getIconWeatherReview(icon));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_review_week, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("datareview", forecast);
        outState.putString("addressreview", address);
        super.onSaveInstanceState(outState);
    }
}
