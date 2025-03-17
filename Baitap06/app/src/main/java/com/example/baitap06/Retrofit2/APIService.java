package com.example.baitap06.Retrofit2;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import com.example.baitap06.Retrofit2.Category;

public interface APIService {
    @GET("categories.php")
    Call<List<Category>> getCategoryAll();
}
