package com.example.hau.weatherapp.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by HAU on 9/22/2015.
 */
public class RecyclerItemClickListener  implements RecyclerView.OnItemTouchListener{
    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
    GestureDetector mGestureDetector;
    public RecyclerItemClickListener(Context context ,OnItemClickListener mListener){
        this.mListener=mListener;
        mGestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View chidView =rv.findChildViewUnder(e.getX(),e.getY());
        if(chidView !=null&&mListener !=null&&mGestureDetector.onTouchEvent(e)){
            mListener.onItemClick(chidView,rv.getChildAdapterPosition(chidView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
