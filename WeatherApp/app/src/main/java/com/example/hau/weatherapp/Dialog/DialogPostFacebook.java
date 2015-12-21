package com.example.hau.weatherapp.Dialog;

import android.content.Context;
import android.graphics.Color;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.hau.weatherapp.Facebook.PostFacebook;
import com.example.hau.weatherapp.Image.IconWeather;
import com.example.hau.weatherapp.R;

/**
 * Created by HAU on 9/21/2015.
 */
public class DialogPostFacebook {
    private Context mContext;
    private TextView decription_dialog, temp_dialog, pressure_dialog, humidity_dialog, wind_dialog;
    private ImageView iconDialog;
    private EditText txtMessage;
    private PostFacebook postFacebook;
    private MaterialDialog dialog = null;

    public DialogPostFacebook(Context mContext, PostFacebook postFacebook) {
        this.mContext = mContext;
        this.postFacebook = postFacebook;
    }

    public void CreateDialog(final String address, final String icon, String temp, String decription, String pressure, String humidity, String wind) {
        String iconweather = icon;
        dialog = new MaterialDialog.Builder(mContext)
                .customView(R.layout.dialog_post_facebook, true)
                .title("CHIA SẼ LÊN FACEBOOK")
                .titleGravity(GravityEnum.CENTER)
                .titleColorRes(R.color.md_material_blue_800)
                .positiveText("CHIA SẼ")
                .positiveColorRes(R.color.md_material_blue_800)
                .negativeText("HỦY")
                .negativeColorRes(R.color.color_text_dialog)
                .backgroundColor(Color.WHITE)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        String mgs = txtMessage.getText().toString();
                        String tempdialog = temp_dialog.getText().toString();
                        String decriptiondialog = decription_dialog.getText().toString();
                        String pressuredialog = pressure_dialog.getText().toString();
                        String humiditydialog = humidity_dialog.getText().toString();
                        String winddialog = wind_dialog.getText().toString();
                        postFacebook.PostStatus(address, mgs, icon, tempdialog, decriptiondialog, pressuredialog, humiditydialog, winddialog);
                    }


                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                    }
                }).build();
        txtMessage = (EditText) dialog.findViewById(R.id.txtMessage);
        decription_dialog = (TextView) dialog.findViewById(R.id.decription_dialog);
        temp_dialog = (TextView) dialog.findViewById(R.id.temp_dialog);
        pressure_dialog = (TextView) dialog.findViewById(R.id.pressure_dialog);
        humidity_dialog = (TextView) dialog.findViewById(R.id.humidity_dialog);
        wind_dialog = (TextView) dialog.findViewById(R.id.wind_dialog);
        iconDialog = (ImageView) dialog.findViewById(R.id.icon_dialog);
        decription_dialog.setText(decription);
        temp_dialog.setText(temp);
        pressure_dialog.setText(pressure);
        humidity_dialog.setText(humidity);
        wind_dialog.setText(wind);
        iconDialog.setImageResource(IconWeather.getIconWeatherReview(iconweather));
    }

    public void showDialog() {
        if (dialog != null) {
            dialog.show();
        }
    }

    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
