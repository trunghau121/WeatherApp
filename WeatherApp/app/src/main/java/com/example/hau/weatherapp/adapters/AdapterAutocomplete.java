package com.example.hau.weatherapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hau.weatherapp.R;

/**
 * Created by HAU on 9/7/2015.
 */
public class AdapterAutocomplete extends CursorAdapter {
    private Context mCon;
    private LayoutInflater mLayoutInflater;

    public AdapterAutocomplete(Context mCon, Cursor mCur) {
        super(mCon, mCur);
        this.mCon = mCon;
        mLayoutInflater = LayoutInflater.from(mCon);


    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = mLayoutInflater.inflate(R.layout.item_search_autocomplete, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView txtAutocomplete = (TextView) view.findViewById(R.id.txtAutocomplete);
        txtAutocomplete.setText(cursor.getString(1));
    }

}
