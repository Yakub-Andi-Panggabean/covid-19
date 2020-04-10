package com.merbuana.covid19.listener;

import android.content.Context;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.merbuana.covid19.R;
import com.merbuana.covid19.model.ProvinceAdapter;
import com.merbuana.covid19.model.response.Province;
import com.merbuana.covid19.service.Covid19Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class AppReadyListener {

    private Covid19Service service;
    private Context context;

    public AppReadyListener(Context context, Covid19Service service) {
        this.service = service;
        this.context = context;
    }


    public void setDetailCases(TextView textLastUpdateDate, TextView textLastUpdateHour, TextView textConfirmedCase, TextView textFatalityCase, TextView textCuredCase, TextView treatmentCase) {
        CompletableFuture.runAsync(() -> {
            service.getCovidCases(resp -> {
                SimpleDateFormat dateFormatDate = new SimpleDateFormat("dd MMMM, yyyy");
                SimpleDateFormat dateFormatTime = new SimpleDateFormat("HH:mm");

                textConfirmedCase.setText(String.valueOf(resp.getNumberOfConfirmedCase()));
                textFatalityCase.setText(String.valueOf(resp.getNumberOfFatalityCase()));
                textCuredCase.setText(String.valueOf(resp.getNumberOfRecoveredCase()));
                textLastUpdateDate.setText(dateFormatDate.format(resp.getLastUpdate()));
                textLastUpdateHour.setText(dateFormatTime.format(resp.getLastUpdate()));
                treatmentCase.setText(String.valueOf(resp.getNumberOfTreatmentCase()));
            }, err -> {
            });
        });
    }


    public void setDrowpdownData(Spinner spinner, TextView textConfirmedCase, TextView textFatalityCase, TextView textCuredCase, TextView treatmentCase) {

        List<Province> provinces = new LinkedList<>();
        AtomicBoolean isDone = new AtomicBoolean(false);

        service.getAllProvinceCodes(resp -> {
            provinces.addAll(resp.stream().sorted((p1, p2) -> Integer.compare(p1.getCode(), p2.getCode())).collect(Collectors.toList()));
            isDone.set(true);
        }, err -> {
            err.printStackTrace();
        });

        //make it synchronous
        while (!isDone.get()) {
        }

        ProvinceAdapter provinceAdapter = new ProvinceAdapter(context, provinces);
        spinner.setAdapter(provinceAdapter);
        spinner.setOnItemSelectedListener(new SearchOnItemSelectedListener(service, textConfirmedCase, textFatalityCase, textCuredCase, treatmentCase));

    }

}
