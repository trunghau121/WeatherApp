package com.example.hau.weatherapp.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.BaseColumns;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import com.example.hau.weatherapp.Image.IconWeather;
import com.example.hau.weatherapp.Network.NetWork;
import com.example.hau.weatherapp.R;

import com.example.hau.weatherapp.adapters.AdapterAutocomplete;
import com.example.hau.weatherapp.adapters.AdapterWeatherInDay;
import com.example.hau.weatherapp.interfaces.RecyclerViewOnClickListener;
import com.example.hau.weatherapp.model.OpenWeatherMap;
import com.example.hau.weatherapp.model.autocomplete.ResultsAuto;
import com.example.hau.weatherapp.model.weatherinday.ListWeatherInDay;
import com.example.hau.weatherapp.model.weatherinday.WeatherInDay;
import com.example.hau.weatherapp.utils.AutocompleteAPI;
import com.example.hau.weatherapp.utils.OpenWeatherAPI;
import com.example.hau.weatherapp.utils.Time;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HAU on 8/27/2015.
 */
public class SearchLocationWeather extends Fragment implements RecyclerViewOnClickListener {
    private TextView txtWind, txtCloudliness, txtPressure, txtHumidty,
            txtSunrise, txtSunset, txtTemp, txtDescription,
            txtLocation, txtDate, txtDay;
    private ImageView ivWeather;
    private NumberFormat format = new DecimalFormat("#0.0");
    private String address = "";
    private CardView cvHome;
    private RelativeLayout rl_container;
    private ResultsAuto resultsAuto;
    private RecyclerView rv_home;
    private AdapterAutocomplete adapterAutocomplete;
    private LinearLayout lnHome;
    private WeatherInDay inDay;
    private MaterialDialog materialDialog;
    private List<ListWeatherInDay> mListl = new ArrayList<ListWeatherInDay>();
    private AdapterWeatherInDay adapterWeatherInDay;
    private TextView tv;
    private MatrixCursor c = new MatrixCursor(new String[]{BaseColumns._ID, "cityName"});
    private PostFacebook facebook = null;
    String temperature, wind, description, pressure, humidity, icon;
    private DialogPostFacebook dialogPostFacebook;
    private OpenWeatherMap resultWeather;
    SearchView searchView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebook = new PostFacebook(getActivity(), this, null);
        facebook.Create();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_location, container, false);
        setHasOptionsMenu(true);
        createView(v);
        adapterAutocomplete = new AdapterAutocomplete(getActivity(), c);
        SharedPreferences mShare = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Gson gson = new Gson();
        int chckLoad = mShare.getInt("checkSearch", -1);
        if (chckLoad == 1) {
            String strResultWeather = mShare.getString("ResultWeatherSearch", "");
            String strInDay = mShare.getString("InDaySearch", "");
            address = mShare.getString("AddressSearch", "");
            if (strResultWeather.length() > 0 && strInDay.length() > 0 && address.length() > 0) {
                resultWeather = gson.fromJson(strResultWeather, OpenWeatherMap.class);
                setDataView(resultWeather);
                inDay = gson.fromJson(strInDay, WeatherInDay.class);
                mListl = inDay.getList();
                adapterWeatherInDay = new AdapterWeatherInDay(getActivity(), mListl);
                rv_home.setAdapter(adapterWeatherInDay);
            } else {
                Toast.makeText(getActivity(), "Hãy refesh lại dữ liệu !", Toast.LENGTH_LONG).show();
            }
        }
        return v;
    }

    public void createView(View v) {
        rl_container = (RelativeLayout) v.findViewById(R.id.rl_container);
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
        cvHome = (CardView) v.findViewById(R.id.cvHome);
        lnHome = (LinearLayout) v.findViewById(R.id.ln_main_home);
        rv_home = (RecyclerView) v.findViewById(R.id.rv_list_weather_home);
        rv_home.setHasFixedSize(true);
        rv_home.addOnItemTouchListener(new RecyclerViewTouchClickListener(getActivity(), rv_home, this));
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_home.setLayoutManager(llm);
        adapterWeatherInDay = new AdapterWeatherInDay(getActivity(), mListl);
        rv_home.setAdapter(adapterWeatherInDay);
        lnHome.setVisibility(View.INVISIBLE);
        cvHome = (CardView) v.findViewById(R.id.cvHome);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search_locaiton, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        MenuItem item = menu.findItem(R.id.action_search_location);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            searchView = (SearchView) item.getActionView();
        } else {
            searchView = (SearchView) MenuItemCompat.getActionView(item);
        }
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setSuggestionsAdapter(adapterAutocomplete);
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionClick(int position) {
                if (resultsAuto.getPredictions().size() > 0) {
                    searchView.setQuery(resultsAuto.getPredictions().get(position).getDescription(),
                            true);
                }
                return true;
            }

            @Override
            public boolean onSuggestionSelect(int position) {
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                actionView(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                populateAdapter(s);
                return false;
            }
        });
        searchView.setQuery(address,false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh_search:
                if (address.length() > 0) {
                    materialDialog.show();
                    lnHome.setVisibility(View.INVISIBLE);
                    actionView(address);
                } else {
                    Toast.makeText(getActivity(), "Refresh Faill !", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.action_share_search:
                showDialogPostFacebook();
                break;
        }
        return true;
    }

    public void showDialogPostFacebook() {
        if (NetWork.checkConnect(getContext())) {
            if (facebook != null && resultWeather != null) {
                dialogPostFacebook = new DialogPostFacebook(getActivity(), facebook);
                dialogPostFacebook.CreateDialog(address, icon, temperature, description, pressure, humidity, wind);
                dialogPostFacebook.showDialog();
            } else {
                Toast.makeText(getActivity(), "Share Faill", Toast.LENGTH_LONG).show();
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

    public void actionView(String city) {
        materialDialog = new MaterialDialog.Builder(getActivity())
                .content("Đang tải...Vui lòng đợi!")
                .contentColor(getActivity().getResources().getColor(R.color.colorsecondText))
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .theme(Theme.LIGHT)
                .show();
        if (city.trim().length() > 0) {
            if (NetWork.checkConnect(getActivity())) {
                new AsyncTaskLocation().execute(city);
            } else {
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
    }

    @Override
    public void onClickListener(View view, int position) {
        DialogInDay dialogInDay =new DialogInDay(getActivity());
        dialogInDay.showDialog(mListl.get(position));
    }

    @Override
    public void onLongPressClickListener(View view, int position) {

    }

    class AsyncTaskLocation extends AsyncTask<String, OpenWeatherMap, Void> {

        @Override
        protected Void doInBackground(String... params) {
            resultWeather = OpenWeatherAPI.searchLocationtWeather(params[0]);
            address = params[0];
            inDay = OpenWeatherAPI.getWeatherInDayCity(address);
            publishProgress(resultWeather);
            return null;
        }

        @Override
        protected void onProgressUpdate(OpenWeatherMap... values) {
            super.onProgressUpdate(values);
            mListl = inDay.getList();
            adapterWeatherInDay = new AdapterWeatherInDay(getActivity(), mListl);
            rv_home.setAdapter(adapterWeatherInDay);
            setDataView(values[0]);
            new AsyncTaskSaveSearch().execute();
            materialDialog.dismiss();
        }
    }


    public void setDataView(OpenWeatherMap owp) {
        if (owp != null) {
            String cod = owp.getCod();
            if (!cod.equals("404")) {
                if (tv != null) {
                    tv.setVisibility(View.INVISIBLE);
                }
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
                String time = Time.convertTime(owp.getDt());

                String icon = owp.getWeather().get(0).getIcon();
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
                lnHome.setVisibility(View.VISIBLE);
            } else {
                tv.setVisibility(View.VISIBLE);
                tv = new TextView(getActivity());
                tv.setText("Địa chỉ tìm kiếm không có dữ liệu !");
                tv.setTextColor(getResources().getColor(R.color.colorPrimarytext));
                tv.setId(0);
                tv.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                tv.setGravity(Gravity.CENTER);
                cvHome.addView(tv);
            }
        } else {
            tv.setVisibility(View.VISIBLE);
            tv = new TextView(getActivity());
            tv.setText("Không tải được dữ liệu !");
            tv.setTextColor(getActivity().getResources().getColor(R.color.colorPrimarytext));
            tv.setId(0);
            tv.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            tv.setGravity(Gravity.CENTER);
            cvHome.addView(tv);
        }
    }

    private void populateAdapter(String query) {
        if (query.trim().length() > 0) {
            if (NetWork.checkConnect(getActivity())) {
                new AsyncTaskAutoComplete().execute(query);
            } else {
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

    }

    class AsyncTaskAutoComplete extends AsyncTask<String, ResultsAuto, Void> {
        @Override
        protected Void doInBackground(String... params) {
            ResultsAuto result = AutocompleteAPI.getAutocomplete(params[0]);
            publishProgress(result);
            return null;
        }

        @Override
        protected void onProgressUpdate(ResultsAuto... values) {
            super.onProgressUpdate(values);
            c = new MatrixCursor(new String[]{BaseColumns._ID, "cityName"});
            resultsAuto = values[0];
            for (int i = 0; i < resultsAuto.getPredictions().size(); i++) {
                String address = resultsAuto.getPredictions().get(i).getDescription();
                c.addRow(new Object[]{i, address});
                adapterAutocomplete.changeCursor(c);
            }
        }
    }

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
    class AsyncTaskSaveSearch extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            SaveValue();
            return null;
        }
    }

    public void SaveValue() {
        if (resultWeather != null && inDay != null & address.length() > 0) {
            try {
                SharedPreferences mShare = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = mShare.edit();
                Gson gson = new Gson();
                String jsResultWeather = gson.toJson(resultWeather);
                String jsInDay = gson.toJson(inDay);
                editor.putString("ResultWeatherSearch", jsResultWeather);
                editor.putString("InDaySearch", jsInDay);
                editor.putString("AddressSearch", address);
                editor.putInt("checkSearch", 1);
                editor.apply();
            } catch (Exception ex) {
                Toast.makeText(getActivity(), "Error save value search :" + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }


    }

}
