package com.example.hau.weatherapp.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.net.ConnectivityManagerCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.example.hau.weatherapp.Dialog.DialogInDay;
import com.example.hau.weatherapp.Dialog.DialogPostFacebook;
import com.example.hau.weatherapp.Facebook.PostFacebook;
import com.example.hau.weatherapp.MainActivity;
import com.example.hau.weatherapp.R;
import com.example.hau.weatherapp.adapters.AdapterWeatherInDay;
import com.example.hau.weatherapp.interfaces.RecyclerViewOnClickListener;
import com.example.hau.weatherapp.model.weatherinday.ListWeatherInDay;
import com.example.hau.weatherapp.model.weatherinday.WeatherInDay;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;

import com.example.hau.weatherapp.Image.IconWeather;
import com.example.hau.weatherapp.Network.NetWork;
import com.example.hau.weatherapp.gps.GPSTracker;
import com.example.hau.weatherapp.model.GeocoderAddress;
import com.example.hau.weatherapp.model.OpenWeatherMap;
import com.example.hau.weatherapp.utils.GeocoderAPI;
import com.example.hau.weatherapp.utils.OpenWeatherAPI;
import com.example.hau.weatherapp.utils.Time;
import com.google.gson.Gson;


/**
 * Created by HAU on 8/27/2015.
 */
public class Home extends Fragment implements RecyclerViewOnClickListener {
    private TextView txtWind, txtCloudliness, txtPressure, txtHumidty,
            txtSunrise, txtSunset, txtTemp, txtDescription,
            txtLocation, txtDate, txtTime, txtDay;

    private ImageView ivWeather;
    private NumberFormat format = new DecimalFormat("#0.0");
    public String address = "";
    private RelativeLayout lnHome;
    private LinearLayout lnMainHome;
    private GPSTracker gpsTracker = null;
    private SwipeRefreshLayout sflHome;
    private RecyclerView rv_home;
    private WeatherInDay inDay;
    private List<ListWeatherInDay> mListl = new ArrayList<ListWeatherInDay>();
    private AdapterWeatherInDay adapterWeatherInDay;
    private MaterialDialog materialDialog;
    private TextView tv;
    private OpenWeatherMap resultWeather;
    public String positionHome = "";
    private PostFacebook facebook = null;
    String temperature, wind, description, pressure, humidity, icon;
    private DialogPostFacebook dialogPostFacebook;
    public static Context mContext;
    public int checkAction = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebook = new PostFacebook(getActivity(), this, null);
        facebook.Create();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        mContext = getActivity();
        CreateView(v);
        materialDialog = new MaterialDialog.Builder(getActivity())
                .content("Đang tải...Vui lòng đợi!")
                .contentColor(getActivity().getResources().getColor(R.color.colorsecondText))
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .theme(Theme.LIGHT).build();

        gpsTracker = new GPSTracker(getContext());
        resultWeather = new OpenWeatherMap();
        inDay = new WeatherInDay();

        SharedPreferences mShare = PreferenceManager.getDefaultSharedPreferences(mContext);
        Gson gson = new Gson();
        int chckLoad = mShare.getInt("checkHome", -1);
        if (chckLoad == 1) {
            String strResultWeather = mShare.getString("ResultWeatherHome", "");
            String strInDay = mShare.getString("InDayHome", "");
            address = mShare.getString("Address", "");
            if (strResultWeather.length() > 0 && strInDay.length() > 0 && address.length() > 0) {
                resultWeather = gson.fromJson(strResultWeather, OpenWeatherMap.class);
                setDataView(resultWeather);
                inDay = gson.fromJson(strInDay, WeatherInDay.class);
                mListl = inDay.getList();
                adapterWeatherInDay = new AdapterWeatherInDay(getActivity(), mListl);
                rv_home.setAdapter(adapterWeatherInDay);
            } else {
                Toast.makeText(mContext, "Hãy refesh lại dữ liệu !", Toast.LENGTH_LONG).show();
            }
        } else {
            materialDialog.show();
            actionView();
        }
        return v;
    }

    /**
     * Function create views in Home Fragment
     *
     * @param v
     */
    public void CreateView(View v) {
        txtWind = (TextView) v.findViewById(R.id.txtWindspeed);
        txtCloudliness = (TextView) v.findViewById(R.id.txtCloud);
        txtPressure = (TextView) v.findViewById(R.id.txtPressure);
        txtHumidty = (TextView) v.findViewById(R.id.txtHumidity);
        txtSunrise = (TextView) v.findViewById(R.id.txtSunrise);
        txtSunset = (TextView) v.findViewById(R.id.txtSunset);
        ivWeather = (ImageView) v.findViewById(R.id.ivIcon);
        txtTemp = (TextView) v.findViewById(R.id.txtTemp);
        txtLocation = (TextView) v.findViewById(R.id.txtLocation);
        txtDate = (TextView) v.findViewById(R.id.txtDate);
        txtDay = (TextView) v.findViewById(R.id.txtDay);
        txtDescription = (TextView) v.findViewById(R.id.txtDescription);
        lnMainHome = (LinearLayout) v.findViewById(R.id.ln_main_home);
        lnHome = (RelativeLayout) v.findViewById(R.id.main_home);
        rv_home = (RecyclerView) v.findViewById(R.id.rv_list_weather_home);
        rv_home.setHasFixedSize(true);
        rv_home.addOnItemTouchListener(new RecyclerViewTouchClickListener(getActivity(), rv_home, this));
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_home.setLayoutManager(llm);
        adapterWeatherInDay = new AdapterWeatherInDay(getActivity(), mListl);
        rv_home.setAdapter(adapterWeatherInDay);
        lnMainHome.setVisibility(View.INVISIBLE);
        tv = new TextView(getActivity());
    }

    /**
     * Function take data weather form internet
     */
    public void actionView() {
        if (NetWork.checkConnect(getContext())) {
            if (gpsTracker.canGetLocation()) {
                new AsyncTaskHome().execute();
            } else {
                materialDialog.dismiss();
                new MaterialDialog.Builder(getActivity())
                        .content("Không có GPS.Vui lòng kiểm tra lại!")
                        .positiveText("Kiểm Tra").negativeText("Không")
                        .contentColor(Color.BLACK)
                        .positiveColor(Color.RED)
                        .negativeColor(Color.BLUE)
                        .backgroundColor(Color.WHITE)
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                super.onPositive(dialog);
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                super.onNegative(dialog);
                            }
                        })
                        .show();
            }
        } else {
            materialDialog.dismiss();
            new MaterialDialog.Builder(getActivity())
                    .content("Không có internet.Vui lòng kiểm tra lại!")
                    .positiveText("Kiểm Tra").negativeText("Không")
                    .contentColor(Color.BLACK)
                    .positiveColor(Color.RED)
                    .negativeColor(Color.BLUE)
                    .backgroundColor(Color.WHITE)
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            super.onPositive(dialog);
                            Intent it = new Intent(Settings.ACTION_WIFI_SETTINGS);
                            startActivity(it);
                        }

                        @Override
                        public void onNegative(MaterialDialog dialog) {
                            super.onNegative(dialog);
                        }
                    })
                    .show();

        }
    }

    class AsyncTaskHome extends AsyncTask<Void, OpenWeatherMap, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            resultWeather = OpenWeatherAPI.getCurrentWeather(gpsTracker.getLatitude(),
                    gpsTracker.getLongitude());
            GeocoderAddress resultAddress = GeocoderAPI.getGeocoderAddress(gpsTracker.getLatitude(),
                    gpsTracker.getLongitude());
            address = resultAddress.getResults().get(0).getFormatted_address();
            positionHome = address;
            inDay = OpenWeatherAPI.getWeatherInDayLocation(gpsTracker.getLatitude(),
                    gpsTracker.getLongitude());
            publishProgress(resultWeather);
            return null;
        }

        @Override
        protected void onProgressUpdate(OpenWeatherMap... values) {
            super.onProgressUpdate(values);
            setDataView(values[0]);
            mListl = inDay.getList();
            adapterWeatherInDay = new AdapterWeatherInDay(getActivity(), mListl);
            rv_home.setAdapter(adapterWeatherInDay);
            new AsyncTaskSave().execute();
            materialDialog.dismiss();
        }
    }

    /**
     * Function set data views
     *
     * @param owp
     */
    public void setDataView(OpenWeatherMap owp) {
        if (owp != null) {
            if (tv != null) {
                tv.setVisibility(View.INVISIBLE);
            }
            String cod = owp.getCod();
            temperature = (int) (owp.getMain().getTemp() - 273.15) + "°C";
            wind = owp.getWind().getSpeed() + " m/s";
            String cloudiness = owp.getClouds().getAll() + "%";

            description = owp.getWeather().get(0).getDescription();

            pressure = owp.getMain().getPressure() + " hpa";
            humidity = owp.getMain().getHumidity() + "%";
            String Sunrise = Time.convertTime(owp.getSys().getSunrise());
            String sunset = Time.convertTime(owp.getSys().getSunset());
            String location = address;
            String date = Time.convertDate(owp.getDt());
            String dayofweek = Time.getDayofWeek(owp.getDt());

            icon = owp.getWeather().get(0).getIcon();
            ivWeather.setImageResource(IconWeather.getIconWeatherReview(icon));
            txtWind.setText(wind);
            txtCloudliness.setText(cloudiness);
            txtPressure.setText(pressure);
            txtHumidty.setText(humidity);
            txtSunrise.setText(Sunrise);
            txtSunset.setText(sunset);
            txtLocation.setText(location);
            txtDate.setText(date);
            txtDay.setText(dayofweek);
            txtDescription.setText(description);
            txtTemp.setText(temperature);
            lnMainHome.setVisibility(View.VISIBLE);
        } else {
            tv.setVisibility(View.VISIBLE);
            tv = new TextView(getActivity());
            tv.setText("Không tải được dữ liệu !");
            tv.setTextColor(getResources().getColor(R.color.colorPrimarytext));
            tv.setId(0);
            tv.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            tv.setGravity(Gravity.CENTER);
            lnHome.addView(tv);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                materialDialog.show();
                lnMainHome.setVisibility(View.INVISIBLE);
                actionView();
                break;
            case R.id.action_share:
                showDialogPostFacebook();
                break;
        }
        return true;
    }

    /**
     * Function call and create dialog post faceboook
     */
    public void showDialogPostFacebook() {
        if (NetWork.checkConnect(getContext())) {
            if (gpsTracker.canGetLocation()) {
                if (facebook != null && resultWeather != null) {
                    dialogPostFacebook = new DialogPostFacebook(getActivity(), facebook);
                    dialogPostFacebook.CreateDialog(address, icon, temperature, description, pressure, humidity, wind);
                    dialogPostFacebook.showDialog();
                } else {
                    Toast.makeText(getActivity(), "Share Fail", Toast.LENGTH_LONG).show();
                }
            } else {
                materialDialog.dismiss();
                gpsTracker.showSettingsAlert();
            }
        } else {
            materialDialog.dismiss();
            new MaterialDialog.Builder(getActivity())
                    .content("Không có internet.Vui lòng kiểm tra lại!")
                    .positiveText("Kiểm Tra").negativeText("Không")
                    .contentColor(Color.BLACK)
                    .positiveColor(Color.RED)
                    .negativeColor(Color.BLUE)
                    .backgroundColor(Color.WHITE)
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            super.onPositive(dialog);
                            Intent it = new Intent(Settings.ACTION_WIFI_SETTINGS);
                            startActivity(it);
                        }

                        @Override
                        public void onNegative(MaterialDialog dialog) {
                            super.onNegative(dialog);
                        }
                    })
                    .show();

        }
    }

    /**
     * class take event touch in RecyclerView
     */
    private class RecyclerViewTouchClickListener implements RecyclerView.OnItemTouchListener {
        private Context mCon;
        private GestureDetector mGestureDetector;
        private RecyclerViewOnClickListener mRecyclerViewClickListener;

        public RecyclerViewTouchClickListener(Context mCon, final RecyclerView rv, final RecyclerViewOnClickListener mRecyclerViewClickListener) {
            this.mCon = mCon;
            this.mRecyclerViewClickListener = mRecyclerViewClickListener;
            this.mGestureDetector = new GestureDetector(this.mCon, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);
                    View cv = rv.findChildViewUnder(e.getX(), e.getY());
                    if (cv != null && mRecyclerViewClickListener != null) {
                        mRecyclerViewClickListener.onLongPressClickListener(cv, rv.getChildAdapterPosition(cv));
                    }
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    View cv = rv.findChildViewUnder(e.getX(), e.getY());
                    if (cv != null && mRecyclerViewClickListener != null) {
                        mRecyclerViewClickListener.onClickListener(cv, rv.getChildAdapterPosition(cv));
                    }
                    return true;
                }
            });

        }


        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            mGestureDetector.onTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {


        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    @Override
    public void onClickListener(View view, int position) {
        DialogInDay dialogInDay = new DialogInDay(getActivity());
        dialogInDay.showDialog(mListl.get(position));
    }


    @Override
    public void onLongPressClickListener(View view, int position) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (facebook != null) {
            facebook.ResuftCallbackManager(requestCode, resultCode, data);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(connectionReceiver, filter);
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            getActivity().unregisterReceiver(connectionReceiver);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Create an BroadcastReceiver use check internet
     */
    BroadcastReceiver connectionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().equals(ConnectivityManager
                    .CONNECTIVITY_ACTION)) {

                boolean isConnected = NetWork.checkConnect(mContext);

                if (isConnected) {
                    if (checkAction == 0) {
                        materialDialog.show();
                        actionView();
                        checkAction = 1;
                    }
                } else {
                    checkAction = 0;
                }
            }
        }
    };

    class AsyncTaskSave extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            SaveValue();
            return null;
        }
    }

    public void SaveValue() {
        if (resultWeather != null && inDay != null & address.length() > 0) {
            try {
                SharedPreferences mShare = PreferenceManager.getDefaultSharedPreferences(mContext);
                SharedPreferences.Editor editor = mShare.edit();
                Gson gson = new Gson();
                String jsResultWeather = gson.toJson(resultWeather);
                String jsInDay = gson.toJson(inDay);
                editor.putString("ResultWeatherHome", jsResultWeather);
                editor.putString("InDayHome", jsInDay);
                editor.putString("Address", address);
                editor.putInt("checkHome", 1);
                editor.apply();
            } catch (Exception ex) {
                Toast.makeText(mContext, "Error save value home" + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }


    }


}
