package com.example.foodplaner.network;

import com.example.foodplaner.models.AreaResponse;
import com.example.foodplaner.models.CategoryFilterResponse;
import com.example.foodplaner.models.CategoryResponse;
import com.example.foodplaner.models.IngredientResponse;
import com.example.foodplaner.models.MealResponse;

import io.reactivex.rxjava3.core.Single;

public interface MealsRemoteDataSource {
    Single<MealResponse> getCategoryMeals(String cat);
    Single<MealResponse> getMealOfTheDay();
    Single<MealResponse> getSuggestedMeals();
    Single<CategoryResponse> getCategories();
    Single<AreaResponse> getAreas();
    Single<IngredientResponse> getIngredients();
    Single<MealResponse> getSelectedMeal(String id);
    Single<MealResponse> getMealsByIngredient(String ing);
    Single<MealResponse> getMealsByArea(String area);
    Single<MealResponse> searchMeal(String name);
}
