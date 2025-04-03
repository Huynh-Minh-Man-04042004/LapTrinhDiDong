package com.hcmute.baitap09;

import android.os.Message;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ServiceAPI {

    // URL cơ bản của API
    public static final String BASE_URL = "http://app.iotstar.vn:8081/appfoods/";

    // Gson converter để làm việc với dữ liệu JSON
    Gson gson = new GsonBuilder().setDateFormat("yyyy MM dd HH:mm:ss").create();

    // Retrofit client
    ServiceAPI serviceAPI = new Retrofit.Builder()
            .baseUrl(BASE_URL) // Địa chỉ của API
            .addConverterFactory(GsonConverterFactory.create(gson)) // Chuyển đổi JSON sang các đối tượng Java
            .build()
            .create(ServiceAPI.class); // Tạo một instance của ServiceAPI

    // Phương thức upload ảnh với Multipart
    @Multipart
    @POST("upload.php")
    Call<List<ImageUpload>> upload(@Part(Const.MY_USERNAME) RequestBody username,
                                   @Part MultipartBody.Part avatar);

    // Phương thức upload với endpoint khác (upload1.php)
    @Multipart
    @POST("updateimages.php")
    Call<Message> upload1(@Part(Const.MY_USERNAME) RequestBody username,
                          @Part MultipartBody.Part avatar);
}
