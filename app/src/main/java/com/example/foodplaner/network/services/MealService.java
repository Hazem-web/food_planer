package com.example.foodplaner.network.services;

import com.example.foodplaner.models.MealResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
    @GET("search.php")
    Single<MealResponse> getMealsByName(@Query("s") String name);

    @GET("search.php")
    Single<MealResponse> getMealsByFirstLetter(@Query("f") String firstLetter);

    @GET("lookup.php")
    Single<MealResponse> getMealById(@Query("i") String id);

    @GET("random.php")
    Single<MealResponse> getMealOfTheDay();

    @GET("filter.php")
    Single<MealResponse> getMealsByIngredient(@Query("i") String ingredient);

    @GET("filter.php")
    Single<MealResponse> getMealsByCategory(@Query("c") String category);

    @GET("filter.php")
    Single<MealResponse> getMealsByArea(@Query("a") String area);
}
