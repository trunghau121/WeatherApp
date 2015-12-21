package com.example.hau.weatherapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.hau.weatherapp.Image.IconWeather;
import com.example.hau.weatherapp.R;
import com.example.hau.weatherapp.interfaces.RecyclerViewOnClickListener;
import com.example.hau.weatherapp.model.sixteendayweatherforecast.ListForecast;
import com.example.hau.weatherapp.utils.Time;

import java.util.List;

/**
 * Created by HAU on 9/9/2015.
 */
public class AdapterSixteenWeather extends RecyclerView.Adapter<AdapterSixteenWeather.MyViewHolder> {
    private RecyclerViewOnClickListener mRecyclerViewOnClickListener;
    private List<ListForecast> mList;
    private LayoutInflater mLayoutInflater;
    private Context mCon;

    public AdapterSixteenWeather(Context mCon, List<ListForecast> mList) {
        this.mCon = mCon;
        this.mList = mList;
        mLayoutInflater = LayoutInflater.from(mCon);

    }

    public void addListItem(ListForecast l, int position) {
        mList.add(l);
        notifyItemInserted(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.item_weather, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String date = Time.convertDateItem(mList.get(position).getDt());
        String dayofweek = Time.getDayofWeek(mList.get(position).getDt());
        String temp = (int) (mList.get(position).getTemp().getDay()) + "°C";
        String icon = mList.get(position).getWeather().get(0).getIcon();
        holder.txtDataItem.setText(dayofweek);
        holder.txtTimeItem.setText(date);
        holder.txtTempItem.setText(temp);
        holder.ivItem.setImageResource(IconWeather.getIconWeatherReview(icon));
        try {
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(holder.ln_mainItem);
        } catch (Exception ex) {

        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtDataItem, txtTimeItem, txtTempItem;
        ImageView ivItem;
        LinearLayout ln_mainItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            ln_mainItem = (LinearLayout) itemView.findViewById(R.id.ln_mainItem);
            txtDataItem = (TextView) itemView.findViewById(R.id.txtDataItem);
            txtTimeItem = (TextView) itemView.findViewById(R.id.txtTimeItem);
            txtTempItem = (TextView) itemView.findViewById(R.id.txtTempItem);
            ivItem = (ImageView) itemView.findViewById(R.id.ivItem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mRecyclerViewOnClickListener != null) {
                mRecyclerViewOnClickListener.onClickListener(v, getAdapterPosition());
            }
        }
    }
}
