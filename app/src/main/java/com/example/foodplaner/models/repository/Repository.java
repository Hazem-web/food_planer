package com.example.foodplaner.models.repository;

import com.example.foodplaner.models.AreaResponse;
import com.example.foodplaner.models.CategoryFilterResponse;
import com.example.foodplaner.models.CategoryResponse;
import com.example.foodplaner.models.IngredientResponse;
import com.example.foodplaner.models.Meal;
import com.example.foodplaner.models.MealPlanned;
import com.example.foodplaner.models.MealResponse;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface Repository {
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
    Flowable<List<Meal>> getFavMeals();
    Single<Meal> getFavMealById(String id);
    Completable insertFavMeal(Meal meal);
    Completable deleteFavMeal(Meal meal);
    Flowable<List<MealPlanned>> getPlannedMealsByDate(Date date);
    Completable insertPlannedMeal(MealPlanned meal);
    Completable deletePlannedMeal(MealPlanned meal);
}
