package com.merbuana.tgb2.service.bean;

import android.content.Context;

import com.merbuana.tgb2.model.response.CaseResponse;
import com.merbuana.tgb2.model.response.Province;
import com.merbuana.tgb2.service.TGB2Service;
import com.merbuana.tgb2.util.SingletonUtil;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class TGB2ServiceBean implements TGB2Service {

    private static List<Province> COUNTRY_CODE = new ArrayList<>();

    private Context context;

    public TGB2ServiceBean(Context context) {
        this.context = context;
    }

    @Override
    public void getCases(Consumer<CaseResponse> onSuccess, Consumer<Throwable> onError) {

        String uri = SingletonUtil.INSTANCE.getProperty("uri_global", context);
        String path = SingletonUtil.INSTANCE.getProperty("path_global_countries_indonesia", context);


        Request request = new Request.Builder().url(uri + path).build();

        SingletonUtil.INSTANCE.getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                onError.accept(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.body().string());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                try {
                    onSuccess.accept(new CaseResponse(
                            sdf.parse(jsonNode.get("lastUpdate").getTextValue()),
                            jsonNode.get("confirmed").get("value").getIntValue(),
                            jsonNode.get("recovered").get("value").getIntValue(),
                            jsonNode.get("deaths").get("value").getIntValue()));
                } catch (ParseException e) {
                    onError.accept(e);
                }
            }
        });

    }


    @Override
    public void getCasesIndonesianProvinceDetail(int provinceCode, Consumer<CaseResponse> onSuccess, Consumer<Throwable> onError) {
        accessProvinceApi(resp -> {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode node = objectMapper.readTree(resp).get("data");
                if (node.isArray()) {

                    Optional<JsonNode> matchNode = StreamSupport.stream(node.spliterator(), false).filter(n ->
                            n.get("kodeProvi").getIntValue() == provinceCode
                    ).findAny();

                    matchNode.ifPresent(n -> {
                        onSuccess.accept(new CaseResponse(
                                null,
                                n.get("kasusPosi").getIntValue(),
                                n.get("kasusSemb").getIntValue(),
                                n.get("kasusMeni").getIntValue()
                        ));

                    });
                }
            } catch (IOException e) {
                onError.accept(e);
            }

        }, onError::accept);

    }

    @Override
    public void getAllProvinceCodes(Consumer<List<Province>> onSuccess, Consumer<Throwable> onError) {
        accessProvinceApi(resp -> {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode node = objectMapper.readTree(resp).get("data");
                if (node.isArray()) {
                    StreamSupport.stream(node.spliterator(), false).forEach(n -> {
                        COUNTRY_CODE.add(new Province(n.get("kodeProvi").getIntValue(), n.get("provinsi").getTextValue()));
                    });
                    onSuccess.accept(COUNTRY_CODE);
                }
            } catch (IOException e) {
                onError.accept(e);
            }
        }, onError::accept);
    }

    private void accessProvinceApi(Consumer<String> onSuccess, Consumer<Throwable> onError) {

        String uri = SingletonUtil.INSTANCE.getProperty("uri_indonesia_per_province", context);

        Request request = new Request.Builder().url(uri).build();

        SingletonUtil.INSTANCE.getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                onError.accept(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    onSuccess.accept(response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                    onError.accept(e);
                }
            }
        });

    }


    @Override
    public void getGlobalCases(Consumer<CaseResponse> onSuccess, Consumer<Throwable> onError) {

        String uri = SingletonUtil.INSTANCE.getProperty("uri_global", context);
        Request request = new Request.Builder().url(uri + "/api").build();

        SingletonUtil.INSTANCE.getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                onError.accept(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode node = objectMapper.readTree(response.body().string());
                    int confirmed = node.get("confirmed").get("value").getIntValue();
                    int recovered = node.get("recovered").get("value").getIntValue();
                    int deaths = node.get("deaths").get("value").getIntValue();
                    onSuccess.accept(new CaseResponse(new Date(), confirmed, recovered, deaths));
                } catch (Exception e) {
                    e.printStackTrace();
                    onError.accept(e);
                }
            }
        });

    }


}

