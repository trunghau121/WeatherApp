package com.example.hau.weatherapp.Facebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.widget.Toast;

import com.example.hau.weatherapp.Image.IconWeather;
import com.example.hau.weatherapp.R;
import com.example.hau.weatherapp.model.OpenWeatherMap;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;

import java.util.Arrays;

/**
 * Created by HAU on 9/21/2015.
 */
public class PostFacebook {
    private CallbackManager callbackManager;
    private Fragment fragment;
    private Activity mActivity;
    private Context mCon;
    private ShareApi api;

    public PostFacebook(Context mCon, Fragment fragment, Activity mActivity) {
        this.mCon = mCon;
        this.fragment = fragment;
        this.mActivity = mActivity;
    }

    public void Create() {
        FacebookSdk.sdkInitialize(mCon.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    public void PostStatus(final String address, final String msg, final String icon, final String temp, final String decription, final String pressure, final String humidity, final String wind) {
        Resources res = mCon.getResources();
        String tempMain = "Nhiệt độ : " + temp;
        String decrMain = "Bầu trời : " + decription;
        String presMain = "Áp suất : " + pressure;
        String humMain = "Độ ẩm : " + humidity;
        String speesWind = "Tốc độ gió : " + wind;
        int id = IconWeather.getIconWeatherReview(icon);
        Bitmap b = BitmapFactory.decodeResource(res, id);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(b)
                .build();
        SharePhotoContent photoContent = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
        api = new ShareApi(photoContent);
        String content = "";
        if (msg.length() > 0) {
            content = (Html.fromHtml("<h1>" + msg + "</h1></br><h1>" + address + "</h1></br><h1>" + tempMain + "</h1></br><h1>" + decrMain + "</h1></br><h1>" + presMain + "</h1></br><h1>" + humMain + "</h1></br><h1>" + speesWind + "</h1>")).toString();
        } else {
            content = (Html.fromHtml("<h1>" + address + "</h1></br><h1>" + tempMain + "</h1></br><h1>" + decrMain + "</h1></br><h1>" + presMain + "</h1></br><h1>" + humMain + "</h1></br><h1>" + speesWind)).toString();
        }
        api.setMessage(content);
        api.share(new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(mCon, "Chia sẽ lên facebook thành công !", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {
                if (fragment != null) {
                    LoginManager.getInstance().logInWithPublishPermissions(fragment, Arrays.asList("publish_actions"));
                    LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            PostStatus(address, msg, icon, temp, decription, pressure, humidity, wind);
                        }

                        @Override
                        public void onCancel() {

                        }

                        @Override
                        public void onError(FacebookException e) {

                        }
                    });
                } else {
                    LoginManager.getInstance().logInWithPublishPermissions(mActivity, Arrays.asList("publish_actions"));
                    LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            PostStatus(address, msg, icon, temp, decription, pressure, humidity, wind);
                        }

                        @Override
                        public void onCancel() {

                        }

                        @Override
                        public void onError(FacebookException e) {

                        }
                    });

                }
            }
        });
    }

    public void ResuftCallbackManager(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
