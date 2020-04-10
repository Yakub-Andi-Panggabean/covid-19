package com.merbuana.covid19.listener;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.merbuana.covid19.model.response.Province;
import com.merbuana.covid19.service.Covid19Service;

import java.text.SimpleDateFormat;
import java.util.concurrent.CompletableFuture;

public class SearchOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

    private Covid19Service service;
    private TextView textConfirmedCase;
    private TextView textFatalityCase;
    private TextView textCuredCase;
    private TextView treatmentCase;

    public SearchOnItemSelectedListener(Covid19Service service, TextView textConfirmedCase, TextView textFatalityCase, TextView textCuredCase, TextView treatmentCase) {
        this.service = service;
        this.textConfirmedCase = textConfirmedCase;
        this.textFatalityCase = textFatalityCase;
        this.textCuredCase = textCuredCase;
        this.treatmentCase = treatmentCase;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Province province = (Province) parent.getSelectedItem();

        if (province.getCode() == 0) {

            CompletableFuture.runAsync(() -> {
                service.getCovidCases(resp -> {

                    textConfirmedCase.setText(String.valueOf(resp.getNumberOfConfirmedCase()));
                    textFatalityCase.setText(String.valueOf(resp.getNumberOfFatalityCase()));
                    textCuredCase.setText(String.valueOf(resp.getNumberOfRecoveredCase()));
                    treatmentCase.setText(String.valueOf(resp.getNumberOfTreatmentCase()));

                }, err -> {
                });
            });

        } else {

            service.getCovidCasesIndonesianProvinceDetail(province.getCode(), resp -> {

                textConfirmedCase.setText(String.valueOf(resp.getNumberOfConfirmedCase()));
                textFatalityCase.setText(String.valueOf(resp.getNumberOfFatalityCase()));
                textCuredCase.setText(String.valueOf(resp.getNumberOfRecoveredCase()));
                treatmentCase.setText(String.valueOf(resp.getNumberOfTreatmentCase()));

            }, err -> {
                err.printStackTrace();
            });

        }


    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }

}
