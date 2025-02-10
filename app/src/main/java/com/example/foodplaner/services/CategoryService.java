package com.example.foodplaner.services;

import com.example.foodplaner.models.CategoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {
    @GET("categories.php")
    Call<CategoryResponse> getCategories();


}
