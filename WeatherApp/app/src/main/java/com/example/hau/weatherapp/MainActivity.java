package com.example.hau.weatherapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.hau.weatherapp.adapters.AdapterMainActivity;
import com.example.hau.weatherapp.utils.RecyclerItemClickListener;
import com.example.hau.weatherapp.fragments.Home;
import com.example.hau.weatherapp.fragments.SearchLocationWeather;
import com.example.hau.weatherapp.fragments.Weatherweek;

public class MainActivity extends AppCompatActivity {
    int TITLES[] = {R.string.current_weather, R.string.postion_weather, R.string.week_weather};
    int ICONS[] = {R.drawable.home,
            R.drawable.jsfiddle,
            R.drawable.pinterest_box,
            R.drawable.pocket
    };
    String NAME = "Nguyễn Trung Hậu";
    String EMAIL = "trunghau121@gmail.com";
    int PROFILE = R.drawable.download;
    private Toolbar toolbar;
    private RecyclerView rvMain;
    private AdapterMainActivity adapter;
    RecyclerView.LayoutManager layoutManager;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    Fragment fr_home=null,fr_week=null,fr_googlemap=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Ứng Dụng Thời Tiết");
        setSupportActionBar(toolbar);
        rvMain = (RecyclerView) findViewById(R.id.rv_mainActivity);
        adapter = new AdapterMainActivity(TITLES, ICONS, NAME, EMAIL, PROFILE, this);
        rvMain.setAdapter(adapter);

        rvMain.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Fragment fra = null;
                if (position != 0) {
                    AdapterMainActivity.selected_item = position;
                    rvMain.getAdapter().notifyDataSetChanged();
                    switch (position) {
                        case 1:
                            if(fr_home ==null){
                                fr_home = new Home();
                                fra =fr_home;
                            }else{
                                fra=fr_home;
                            }
                            break;
                        case 2:
                            fra = new SearchLocationWeather();
                            break;
                        case 3:
                            if(fr_week ==null){
                                fr_week = new Weatherweek();
                                fra =fr_week;
                            }else{
                                fra=fr_week;
                            }
                            break;
                    }

                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.rl_fragment_container, fra);
                    ft.commit();
                    drawerLayout.closeDrawers();
                }
            }
        }));

        layoutManager = new LinearLayoutManager(this);
        rvMain.setLayoutManager(layoutManager);

        AdapterMainActivity.selected_item = 1;
        rvMain.getAdapter().notifyDataSetChanged();
        fr_home=new Home();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.rl_fragment_container, fr_home);
        ft.commit();

        drawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.closed) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences mShare = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = mShare.edit();
        editor.putInt("checkHome", 0);
        editor.putInt("checkWeek", 0);
        editor.putInt("checkSearch", 0);
        editor.apply();
    }
}
