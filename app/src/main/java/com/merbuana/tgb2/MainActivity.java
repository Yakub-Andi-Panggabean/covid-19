package com.merbuana.tgb2;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.merbuana.tgb2.listener.AppReadyListener;
import com.merbuana.tgb2.listener.ChartListener;
import com.merbuana.tgb2.listener.ImageButtonListener;
import com.merbuana.tgb2.service.TGB2Service;
import com.merbuana.tgb2.service.bean.TGB2ServiceBean;

/**
 *
 * name : yakub andi panggabean
 * nim :41819120090
 *
 */

public class MainActivity extends AppCompatActivity {

    private TGB2Service tgb2Service;

    private TextView mortalityText;
    private TextView curedText;
    private TextView confirmedText;
    private TextView treatmentText;
    private TextView textUpdateDate;
    private TextView textUpdateTime;
    private Spinner spinner;
    private ImageButton infoButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        mortalityText = findViewById(R.id.mortalityData);
        curedText = findViewById(R.id.curedData);
        confirmedText = findViewById(R.id.confirmedData);
        textUpdateDate = findViewById(R.id.txtYearDate);
        textUpdateTime = findViewById(R.id.txtHourTime);
        treatmentText = findViewById(R.id.treatmentData);
        spinner = findViewById(R.id.spinnerRegion);
        infoButton = findViewById(R.id.imageButtonInfo);
        infoButton.setOnClickListener(new ImageButtonListener(this));

        tgb2Service = new TGB2ServiceBean(getApplicationContext());

        AppReadyListener appReadyListener = new AppReadyListener(this, tgb2Service);
        appReadyListener.setDetailCases(textUpdateDate, textUpdateTime, confirmedText, mortalityText, curedText, treatmentText);
        appReadyListener.setDrowpdownData(spinner, confirmedText, mortalityText, curedText, treatmentText);


        ChartListener chartListener = new ChartListener(tgb2Service);

        PieChart pieChart = findViewById(R.id.piechart);
        chartListener.initChart(pieChart);

    }


}