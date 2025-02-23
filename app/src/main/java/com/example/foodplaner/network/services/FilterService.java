package com.example.foodplaner.network.services;

import com.example.foodplaner.models.AreaResponse;
import com.example.foodplaner.models.CategoryFilterResponse;
import com.example.foodplaner.models.CategoryResponse;
import com.example.foodplaner.models.IngredientResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface FilterService {
    @GET("categories.php")
    Single<CategoryResponse> getFilterCategories();

    @GET("list.php?a=list")
    Single<AreaResponse> getFilterAreas();

    @GET("list.php?i=list")
    Single<IngredientResponse> getFilterIngredients();



}
