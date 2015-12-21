package com.example.hau.weatherapp.interfaces;

import android.view.View;

/**
 * Created by HAU on 8/21/2015.
 */
public interface RecyclerViewOnClickListener {
    public void onClickListener(View view, int position);

    public void onLongPressClickListener(View view, int position);
}
