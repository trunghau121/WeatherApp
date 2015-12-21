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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
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
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.example.hau.weatherapp.Dialog.DialogPostFacebook;
import com.example.hau.weatherapp.Facebook.PostFacebook;
import com.example.hau.weatherapp.Image.IconWeather;
import com.example.hau.weatherapp.MainActivity;
import com.example.hau.weatherapp.Network.NetWork;
import com.example.hau.weatherapp.R;
import com.example.hau.weatherapp.ReviewWeather;
import com.example.hau.weatherapp.adapters.AdapterSixteenWeather;
import com.example.hau.weatherapp.adapters.AdapterWeatherInDay;
import com.example.hau.weatherapp.gps.GPSTracker;
import com.example.hau.weatherapp.interfaces.RecyclerViewOnClickListener;
import com.example.hau.weatherapp.model.GeocoderAddress;
import com.example.hau.weatherapp.model.OpenWeatherMap;
import com.example.hau.weatherapp.model.sixteendayweatherforecast.ListForecast;
import com.example.hau.weatherapp.model.sixteendayweatherforecast.MainForecast;
import com.example.hau.weatherapp.model.weatherinday.WeatherInDay;
import com.example.hau.weatherapp.utils.GeocoderAPI;
import com.example.hau.weatherapp.utils.OpenWeatherAPI;
import com.example.hau.weatherapp.utils.Time;
import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by HAU on 8/27/2015.
 */
public class Weatherweek extends Fragment implements View.OnClickListener, RecyclerViewOnClickListener {
    private FrameLayout frweek;
    private LinearLayout ln_main_week;
    private TextView txtWindweek, txtCloudlinessweek, txtPressureweek, txtHumidtyweek,
            txtTempweek, txtDescriptionweek,
            txtDateweek, txtDayweek, txtSunriseWeek, txtSunsetWeek, txtLocationWeek;

    private GPSTracker gpsTracker = null;
    private ImageView ivWeek;
    private RecyclerView rv_week;
    private TextView tv, txtVitri, txtCnt;
    private FloatingActionButton fabweek;
    private View positiveAction;
    private List<ListForecast> mListl = new ArrayList<ListForecast>();
    private AdapterSixteenWeather adapterSixteenWeather;
    private String address = "";
    private String cnt = "7";
    private int check = 0;
    private MainForecast forecastWeather;
    private MaterialDialog materialDialog;
    public static String positionCity;
    private PostFacebook facebook = null;
    String tempreview, wind, descriptionreview, pressure, humidity, icon;
    private DialogPostFacebook dialogPostFacebook;
    public int checkAction = 1;
    public Context mContext;
    private OpenWeatherMap resultWeather;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebook = new PostFacebook(getActivity(), this, null);
        facebook.Create();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_week, container, false);
        setHasOptionsMenu(true);
        mContext = getActivity();
        createView(v);
        forecastWeather = new MainForecast();
        resultWeather = new OpenWeatherMap();
        SharedPreferences mShare = PreferenceManager.getDefaultSharedPreferences(mContext);
        Gson gson = new Gson();
        int chckWeek = mShare.getInt("checkWeek", -1);
        if (chckWeek == 1) {
            String strResultWeatherWeek = mShare.getString("ResultWeatherWeek", "");
            String strMainForecast = mShare.getString("MainForecast", "");
            address = mShare.getString("AddressWeek", "");
            if (strResultWeatherWeek.length() > 0 && strMainForecast.length() > 0 && address.length() > 0) {
                resultWeather = gson.fromJson(strResultWeatherWeek, OpenWeatherMap.class);
                addActionView(resultWeather);
                forecastWeather = gson.fromJson(strMainForecast, MainForecast.class);
                mListl = forecastWeather.getList();
                adapterSixteenWeather = new AdapterSixteenWeather(getActivity(), mListl);
                rv_week.setAdapter(adapterSixteenWeather);
            } else {
                Toast.makeText(mContext, "Hãy refesh lại dữ liệu !", Toast.LENGTH_LONG).show();
            }
        }else {
            gpsTracker = new GPSTracker(getActivity());
            materialDialog = new MaterialDialog.Builder(getActivity())
                    .content("Đang tải...Vui lòng đợi!")
                    .contentColor(getActivity().getResources().getColor(R.color.colorsecondText))
                    .progress(true, 0)
                    .progressIndeterminateStyle(false)
                    .theme(Theme.LIGHT)
                    .show();
            actionView();
        }
        return v;
    }

    public void createView(View v) {

        frweek = (FrameLayout) v.findViewById(R.id.fr_week);
        ln_main_week = (LinearLayout) v.findViewById(R.id.ln_main_week);
        ln_main_week.setVisibility(View.INVISIBLE);
        txtWindweek = (TextView) v.findViewById(R.id.txtWindspeedweek);
        txtCloudlinessweek = (TextView) v.findViewById(R.id.txtCloudweek);
        txtPressureweek = (TextView) v.findViewById(R.id.txtPressureweek);
        txtHumidtyweek = (TextView) v.findViewById(R.id.txtHumidityweek);
        txtTempweek = (TextView) v.findViewById(R.id.txtTempweek);
        txtDateweek = (TextView) v.findViewById(R.id.txtDateweek);
        txtDayweek = (TextView) v.findViewById(R.id.txtDayweek);
        txtSunriseWeek = (TextView) v.findViewById(R.id.txtSunriseWeek);
        txtSunsetWeek = (TextView) v.findViewById(R.id.txtSunsetWeek);
        txtLocationWeek = (TextView) v.findViewById(R.id.txtLocationWeek);
        txtDescriptionweek = (TextView) v.findViewById(R.id.txtDescriptionweek);
        ivWeek = (ImageView) v.findViewById(R.id.ivWeek);
        fabweek = (FloatingActionButton) v.findViewById(R.id.fabSearch);
        fabweek.setOnClickListener(this);
        rv_week = (RecyclerView) v.findViewById(R.id.rv_list_weather_week);
        rv_week.setHasFixedSize(true);
        rv_week.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dx > 0) {
                    fabweek.hide(true);
                } else {
                    fabweek.show(true);
                }
            }
        });
        rv_week.addOnItemTouchListener(new RecyclerViewTouchClickListener(getActivity(), rv_week, this));
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_week.setLayoutManager(llm);
        adapterSixteenWeather = new AdapterSixteenWeather(getActivity(), mListl);
        rv_week.setAdapter(adapterSixteenWeather);
    }

    public void addActionView(OpenWeatherMap owp) {
        if (owp != null) {
            if (tv != null) {
                tv.setVisibility(View.INVISIBLE);
            }
            txtLocationWeek.setText(address);
            String cod = owp.getCod();
            tempreview = (int) (owp.getMain().getTemp() - 273.15) + "°C";
            wind = owp.getWind().getSpeed() + " m/s";
            String cloudiness = owp.getClouds().getAll() + "%";
            descriptionreview = owp.getWeather().get(0).getDescription();

            pressure = owp.getMain().getPressure() + " hpa";
            humidity = owp.getMain().getHumidity() + "%";
            String Sunrise = Time.convertTime(owp.getSys().getSunrise());
            String sunset = Time.convertTime(owp.getSys().getSunset());
            String date = Time.convertDate(owp.getDt());
            String dayofweek = Time.getDayofWeek(owp.getDt());

            icon = owp.getWeather().get(0).getIcon();
            txtDateweek.setText(date);
            txtDayweek.setText(dayofweek);
            txtTempweek.setText(tempreview);
            txtDescriptionweek.setText(descriptionreview);
            txtSunriseWeek.setText(Sunrise);
            txtSunsetWeek.setText(sunset);
            txtWindweek.setText(wind);
            txtCloudlinessweek.setText(cloudiness);
            txtPressureweek.setText(pressure);
            txtHumidtyweek.setText(humidity);
            ivWeek.setImageResource(IconWeather.getIconWeatherReview(icon));
            ln_main_week.setVisibility(View.VISIBLE);
        } else {
            ln_main_week.setVisibility(View.VISIBLE);
            tv.setVisibility(View.VISIBLE);
            tv = new TextView(getActivity());
            tv.setText("Không tải được dữ liệu !");
            tv.setTextColor(getResources().getColor(R.color.colorPrimarytext));
            tv.setId(0);
            tv.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            tv.setGravity(Gravity.CENTER);
            ln_main_week.addView(tv);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabSearch:
                ShowDialog();
                break;
        }

    }

    public void ShowDialog() {
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .customView(R.layout.custom_dialog_week, true)
                .title("Thời tiết các ngày kế tiếp")
                .titleColorRes(R.color.md_material_blue_800)
                .positiveText("GO")
                .positiveColor(Color.WHITE)
                .negativeText("CANCE")
                .negativeColorRes(R.color.color_text_dialog)
                .btnSelector(R.drawable.md_btn_selector_custom, DialogAction.POSITIVE)
                .backgroundColor(Color.WHITE)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        address = txtVitri.getText().toString().trim();
                        cnt = txtCnt.getText().toString();
                        check = 1;
                        actionView();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                    }
                }).build();
        positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
        txtVitri = (TextView) dialog.findViewById(R.id.txtVitri);
        txtCnt = (TextView) dialog.findViewById(R.id.txtCnt);
        txtVitri.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                positiveAction.setEnabled(checkInput());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtCnt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                positiveAction.setEnabled(checkInput());
                if (txtCnt.getText().toString().trim().length() > 0) {
                    if (Integer.parseInt(txtCnt.getText().toString()) > 0 && Integer.parseInt(txtCnt.getText().toString()) < 17) {
                        txtCnt.setError(null);
                    } else {
                        txtCnt.setError(Html.fromHtml("<font color='black'>số từ 1 -> 16</font>"));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dialog.show();
        positiveAction.setEnabled(false);

    }

    public boolean checkInput() {
        boolean chk = false;
        if (txtVitri.getText().toString().trim().length() > 0 &&
                txtCnt.getText().toString().trim().length() > 0) {
            chk = true;
        }
        return chk;
    }

    public void actionView() {
        if (NetWork.checkConnect(getContext())) {
            if (gpsTracker.canGetLocation()) {
                if (check == 0) {
                    new AsyncTaskWeekCurrent().execute();
                } else {
                    new AsyncTaskWeek().execute(address);
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

    class AsyncTaskWeekCurrent extends AsyncTask<Void, List<ListForecast>, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            GeocoderAddress resultAddress = GeocoderAPI.getGeocoderAddress(gpsTracker.getLatitude(),
                    gpsTracker.getLongitude());
            address = resultAddress.getResults().get(0).getFormatted_address();
            resultWeather = OpenWeatherAPI.getCurrentWeather(gpsTracker.getLatitude(),
                    gpsTracker.getLongitude());
            positionCity = resultAddress.getResults().get(0).getFormatted_address();
            forecastWeather = OpenWeatherAPI.searchSixteenWeather(address, cnt);
            List<ListForecast> list = null;
            try {
                list = forecastWeather.getList();
            } catch (Exception ex) {
                Log.d("AsyncTaskWeekCurrent", ex.getMessage());
            }
            publishProgress(list);
            return null;
        }

        @Override
        protected void onProgressUpdate(List<ListForecast>... values) {
            mListl = new ArrayList<ListForecast>();
            mListl = values[0];
            addActionView(resultWeather);
            mListl.remove(0);
            adapterSixteenWeather = new AdapterSixteenWeather(getActivity(), mListl);
            rv_week.setAdapter(adapterSixteenWeather);
            materialDialog.dismiss();
            new AsyncTaskSaveWeek().execute();
            super.onProgressUpdate(values);
        }
    }

    class AsyncTaskWeek extends AsyncTask<String, List<ListForecast>, Void> {
        @Override
        protected Void doInBackground(String... params) {
            GeocoderAddress resultAddress = null;
            try {
                resultAddress = GeocoderAPI.getGeocoderCity(address);
                address = resultAddress.getResults().get(0).getFormatted_address();
                resultWeather = OpenWeatherAPI.searchLocationtWeather(address);
            } catch (Exception ex) {
            }
            positionCity = address;
            forecastWeather = OpenWeatherAPI.searchSixteenWeather(address, cnt);
            List<ListForecast> list = forecastWeather.getList();
            publishProgress(list);
            return null;
        }

        @Override
        protected void onProgressUpdate(List<ListForecast>... values) {
            mListl = new ArrayList<ListForecast>();
            mListl = values[0];
            addActionView(resultWeather);
            mListl.remove(0);
            adapterSixteenWeather = new AdapterSixteenWeather(getActivity(), mListl);
            rv_week.setAdapter(adapterSixteenWeather);
            materialDialog.dismiss();
            ln_main_week.setVisibility(View.VISIBLE);
            new AsyncTaskSaveWeek().execute();
            super.onProgressUpdate(values);
        }
    }

    @Override
    public void onClickListener(View view, int position) {
        Intent it = new Intent(getActivity(), ReviewWeather.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("ListForecast", mListl.get(position));
        it.putExtra("dataweek", bundle);
        getActivity().startActivity(it);
    }

    @Override
    public void onLongPressClickListener(View view, int position) {

    }

    public class RecyclerViewTouchClickListener implements RecyclerView.OnItemTouchListener {
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                materialDialog.show();
                ln_main_week.setVisibility(View.INVISIBLE);
                actionView();
                break;
            case R.id.action_share:
                showDialogPostFacebook();
                break;
        }
        return true;
    }

    public void showDialogPostFacebook() {
        if (NetWork.checkConnect(getContext())) {
            if (gpsTracker.canGetLocation()) {
                if (facebook != null && resultWeather != null) {
                    dialogPostFacebook = new DialogPostFacebook(getActivity(), facebook);
                    dialogPostFacebook.CreateDialog(address, icon, tempreview, descriptionreview, pressure, humidity, wind);
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

    class AsyncTaskSaveWeek extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            SaveValue();
            return null;
        }
    }

    public void SaveValue() {
        if (resultWeather != null && forecastWeather != null & address.length() > 0) {
            try {
                SharedPreferences mShare = PreferenceManager.getDefaultSharedPreferences(mContext);
                SharedPreferences.Editor editor = mShare.edit();
                Gson gson = new Gson();
                String jsResultWeatherWeek = gson.toJson(resultWeather);
                String jsForecastWeather = gson.toJson(forecastWeather);
                editor.putString("ResultWeatherWeek", jsResultWeatherWeek);
                editor.putString("MainForecast", jsForecastWeather);
                editor.putString("AddressWeek", address);
                editor.putInt("checkWeek", 1);
                editor.apply();
            } catch (Exception ex) {
                Toast.makeText(mContext, "Error save value weather week :" + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
