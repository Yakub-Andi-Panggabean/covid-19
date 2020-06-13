package com.merbuana.tgb2.listener;

import android.content.Context;
import android.widget.Spinner;
import android.widget.TextView;

import com.merbuana.tgb2.model.ProvinceAdapter;
import com.merbuana.tgb2.model.response.Province;
import com.merbuana.tgb2.service.TGB2Service;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class AppReadyListener {

    private TGB2Service service;
    private Context context;

    public AppReadyListener(Context context, TGB2Service service) {
        this.service = service;
        this.context = context;
    }


    public void setDetailCases(TextView textLastUpdateDate, TextView textLastUpdateHour, TextView textConfirmedCase, TextView textFatalityCase, TextView textCuredCase, TextView treatmentCase) {
        CompletableFuture.runAsync(() -> {
            service.getCases(resp -> {
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
