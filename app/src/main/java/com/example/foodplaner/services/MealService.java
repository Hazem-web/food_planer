package com.example.foodplaner.services;

import com.example.foodplaner.models.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
    @GET("search.php")
    Call<MealResponse> getMealsByName(@Query("s") String name);

    @GET("search.php")
    Call<MealResponse> getMealsByFirstLetter(@Query("f") String firstLetter);

    @GET("search.php")
    Call<MealResponse> getMealById(@Query("i") String id);

    @GET("random.php")
    Call<MealResponse> getMealOfTheDay();

    @GET("filter.php")
    Call<MealResponse> getMealsByIngredient(@Query("i") String ingredient);

    @GET("filter.php")
    Call<MealResponse> getMealsByCategory(@Query("c") String category);

    @GET("filter.php")
    Call<MealResponse> getMealsByArea(@Query("a") String area);
}
