package com.merbuana.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.merbuana.covid19.listener.AppReadyListener;
import com.merbuana.covid19.listener.ChartListener;
import com.merbuana.covid19.service.Covid19Service;
import com.merbuana.covid19.service.bean.Covid19ServiceBean;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Covid19Service covid19Service;

    private TextView mortalityText;
    private TextView curedText;
    private TextView confirmedText;
    private TextView treatmentText;
    private TextView textUpdateDate;
    private TextView textUpdateTime;
    private Spinner spinner;

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

        covid19Service = new Covid19ServiceBean(getApplicationContext());

        AppReadyListener appReadyListener = new AppReadyListener(this, covid19Service);
        appReadyListener.setDetailCases(textUpdateDate, textUpdateTime, confirmedText, mortalityText, curedText, treatmentText);
        appReadyListener.setDrowpdownData(spinner, confirmedText, mortalityText, curedText, treatmentText);


        ChartListener chartListener = new ChartListener(covid19Service);

        PieChart pieChart = findViewById(R.id.piechart);
        chartListener.initChart(pieChart);

    }


}