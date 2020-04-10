package com.merbuana.covid19.service;


import com.merbuana.covid19.model.response.CovidCaseResponse;
import com.merbuana.covid19.model.response.Province;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface Covid19Service {

    void getCovidCases(Consumer<CovidCaseResponse> onSuccess, Consumer<Throwable> onError);

    void getGlobalCovidCases(Consumer<CovidCaseResponse> onSuccess, Consumer<Throwable> onError);

    void getCovidCasesIndonesianProvinceDetail(int provinceCode, Consumer<CovidCaseResponse> onSuccess, Consumer<Throwable> onError);

    void getAllProvinceCodes(Consumer<List<Province>> onSuccess, Consumer<Throwable> onError);


}
