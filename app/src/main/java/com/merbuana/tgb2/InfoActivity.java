package com.merbuana.tgb2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.merbuana.tgb2.listener.ImageButtonListener;

public class InfoActivity extends AppCompatActivity {


    private ImageButton buttonHome;
    private WebView newsWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);
        buttonHome = findViewById(R.id.imageButtonHome);
        buttonHome.setOnClickListener(new ImageButtonListener(this));
        newsWebView = findViewById(R.id.newsWebView);
        newsWebView.getSettings().setJavaScriptEnabled(true);
        newsWebView.loadUrl("https://www.google.com/search?channel=crow2&client=firefox-b-d&q=covid-19+indonesia");
    }


}
