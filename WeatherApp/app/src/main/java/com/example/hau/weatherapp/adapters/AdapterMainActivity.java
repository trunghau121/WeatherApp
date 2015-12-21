package com.example.hau.weatherapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hau.weatherapp.R;

/**
 * Created by HAU on 9/22/2015.
 */
public class AdapterMainActivity extends RecyclerView.Adapter<AdapterMainActivity.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private int mNavTitles[];
    private int mIcons[];
    Context mContext;
    private String name;
    private int profile;
    private String email;
    public static int selected_item = 0;

    public AdapterMainActivity(int Titles[], int Icons[], String Name, String Email, int Profile, Context passedContext) {
        mNavTitles = Titles;
        mIcons = Icons;
        name = Name;
        email = Email;
        profile = Profile;
        this.mContext = passedContext;
    }

    @Override
    public AdapterMainActivity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
            ViewHolder vhItem = new ViewHolder(v, viewType);
            return vhItem;
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false);
            ViewHolder vhHeader = new ViewHolder(v, viewType);
            return vhHeader;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder.Holderid == 1) {
            if (position == selected_item) {
                holder.tvTextNav.setTextColor(mContext.getResources().getColor(R.color.primary));
                holder.ln.setBackgroundColor(mContext.getResources().getColor(R.color.select_background));
            } else {
                holder.tvTextNav.setTextColor(Color.BLACK);
                holder.ln.setBackgroundColor(Color.WHITE);
            }
            if(position ==1){
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0, 25, 0, 0);
                holder.ln.setLayoutParams(lp);
            }
            holder.tvTextNav.setText(mContext.getString(mNavTitles[position - 1]));
            holder.ivNav.setImageResource(mIcons[position - 1]);
        } else {
            holder.ivprofile.setImageResource(profile);
            holder.Name.setText(name);
            holder.email.setText(email);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemCount() {
        return mNavTitles.length + 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        int Holderid;
        TextView tvTextNav;
        ImageView ivNav;
        ImageView ivprofile;
        TextView Name;
        TextView email;
        LinearLayout ln;

        public ViewHolder(View itemView, int ViewType) {
            super(itemView);
            if (ViewType == TYPE_ITEM) {
                tvTextNav = (TextView) itemView.findViewById(R.id.rowText);
                ivNav = (ImageView) itemView.findViewById(R.id.rowIcon);
                ln = (LinearLayout) itemView.findViewById(R.id.ln_main);
                ln.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                Holderid = 1;
            } else {
                Name = (TextView) itemView.findViewById(R.id.tvName);
                email = (TextView) itemView.findViewById(R.id.tvEmail);
                ivprofile = (ImageView) itemView.findViewById(R.id.circleView);
                Holderid = 0;
            }
        }
    }
}
