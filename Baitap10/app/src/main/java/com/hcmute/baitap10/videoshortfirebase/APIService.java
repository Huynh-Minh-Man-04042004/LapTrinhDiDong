package com.hcmute.baitap10.videoshortfirebase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface APIService {

    // Gọi đến file PHP
    @GET("getvideos.php")
    Call<MessageVideoModel> getVideos();

    // Tạo Gson tùy chỉnh định dạng ngày tháng nếu cần
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy MM dd HH:mm:ss")
            .create();

    // Tạo Retrofit instance
    APIService serviceApi = new Retrofit.Builder()
            .baseUrl("http://app.iotstar.vn:8081/appfoods/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);
}
