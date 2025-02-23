package com.example.foodplaner.database;

import com.example.foodplaner.models.Meal;
import com.example.foodplaner.models.MealPlanned;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealsLocalDataSource {
    Completable insertFav(Meal meal);
    Completable deleteFav(Meal meal);
    Flowable<List<Meal>> getFavMeals();
    Single<Meal> getFavMealById(String id);
    Completable insertPlanned(MealPlanned mealPlanned);
    Completable deletePlanned(MealPlanned mealPlanned);
    Flowable<List<MealPlanned>> getPlannedMealsByDay(Date date);
}
