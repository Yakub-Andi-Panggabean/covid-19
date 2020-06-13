package com.merbuana.tgb2.service;


import com.merbuana.tgb2.model.response.CaseResponse;
import com.merbuana.tgb2.model.response.Province;

import java.util.List;
import java.util.function.Consumer;

public interface TGB2Service {

    void getCases(Consumer<CaseResponse> onSuccess, Consumer<Throwable> onError);

    void getGlobalCases(Consumer<CaseResponse> onSuccess, Consumer<Throwable> onError);

    void getCasesIndonesianProvinceDetail(int provinceCode, Consumer<CaseResponse> onSuccess, Consumer<Throwable> onError);

    void getAllProvinceCodes(Consumer<List<Province>> onSuccess, Consumer<Throwable> onError);


}
