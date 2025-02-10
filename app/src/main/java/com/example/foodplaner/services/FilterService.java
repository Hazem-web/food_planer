package com.example.foodplaner.services;

import com.example.foodplaner.models.AreaResponse;
import com.example.foodplaner.models.CategoryFilterResponse;
import com.example.foodplaner.models.IngredientResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FilterService {
    @GET("list.php?c=list")
    Call<CategoryFilterResponse> getFilterCategories();

    @GET("list.php?a=list")
    Call<AreaResponse> getFilterAreas();

    @GET("list.php?i=list")
    Call<IngredientResponse> getFilterIngredients();



}
