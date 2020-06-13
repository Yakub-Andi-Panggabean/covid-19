package com.merbuana.tgb2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.merbuana.tgb2.listener.ImageButtonListener;

public class HelpActivity extends AppCompatActivity {

    private ImageButton buttonHome;
    private ImageButton buttonInfo;
    private WebView hospitalWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_activity);

        buttonHome = findViewById(R.id.imageButtonHome);
        buttonInfo = findViewById(R.id.imageButtonInfo);
        buttonHome.setOnClickListener(new ImageButtonListener(this));
        buttonInfo.setOnClickListener(new ImageButtonListener(this));
        hospitalWebView = findViewById(R.id.hospitalWebView);
        hospitalWebView.getSettings().setJavaScriptEnabled(true);
        hospitalWebView.loadUrl("https://covid19.go.id/daftar-rumah-sakit-rujukan");

    }
}
