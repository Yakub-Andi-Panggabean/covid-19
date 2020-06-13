package com.merbuana.tgb2.listener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.merbuana.tgb2.HelpActivity;
import com.merbuana.tgb2.InfoActivity;
import com.merbuana.tgb2.MainActivity;
import com.merbuana.tgb2.R;

public class ImageButtonListener implements View.OnClickListener {

    private Context context;

    public ImageButtonListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.imageButtonInfo) {

            Activity activity = (Activity) context;
            Intent intent = new Intent(activity, InfoActivity.class);
            activity.startActivity(intent);
            activity.finish();

        } else if (v.getId() == R.id.imageButtonHome) {

            Activity activity = (Activity) context;
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
            activity.finish();

        } else if (v.getId() == R.id.imageButtonHelp) {

            Activity activity = (Activity) context;
            Intent intent = new Intent(activity, HelpActivity.class);
            activity.startActivity(intent);
            activity.finish();

        }


    }
}
