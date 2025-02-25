package com.example.foodplaner.localmeal.presenter;

import com.example.foodplaner.models.Meal;
import com.example.foodplaner.models.MealPlanned;

import io.reactivex.rxjava3.disposables.Disposable;

public interface LocalMealPresenter {
    Disposable showData(String id,String type);

    Disposable addToFav(Meal meal);

    Disposable removeFromFav(Meal meal);

    Disposable addToPlanned(MealPlanned mealPlanned);

    Disposable showFav(String id);
}
