package com.merbuana.covid19.listener;

import android.graphics.Color;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.merbuana.covid19.model.response.CovidCaseResponse;
import com.merbuana.covid19.service.Covid19Service;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class ChartListener {

    public static final int[] CUSTOM_COLOR = {
            ColorTemplate.rgb("#B9B2B2"), ColorTemplate.rgb("#65C842"), ColorTemplate.rgb("#DE4747"), ColorTemplate.rgb("#EEAA25")
    };

    private Covid19Service service;

    public ChartListener(Covid19Service service) {
        this.service = service;
    }

    public void initChart(PieChart pieChart) {

        CompletableFuture.runAsync(() -> {
            service.getGlobalCovidCases(resp -> {
                PieDataSet pieDataSet = new PieDataSet(getData(resp), "");
                pieDataSet.setColors(CUSTOM_COLOR);

                PieData pieData = new PieData(pieDataSet);
                pieData.setValueTextColor(Color.WHITE);
                pieData.setValueTextSize(15);

                pieChart.getDescription().setEnabled(false);
                pieChart.setDrawEntryLabels(false);
                pieChart.setHoleRadius(50f);
                pieChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
                pieChart.setData(pieData);
                pieChart.invalidate();
            }, err -> {
                err.printStackTrace();
            });

        });


    }

    private ArrayList getData(CovidCaseResponse response) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(response.getNumberOfTreatmentCase(), "Dirawat"));
        entries.add(new PieEntry(response.getNumberOfRecoveredCase(), "Sembuh"));
        entries.add(new PieEntry(response.getNumberOfFatalityCase(), "Meninggal"));
        entries.add(new PieEntry(response.getNumberOfConfirmedCase(), "Terkonfirmasi"));
        return entries;
    }

}
