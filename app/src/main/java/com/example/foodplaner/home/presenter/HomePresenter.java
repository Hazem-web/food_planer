package com.example.foodplaner.home.presenter;

import com.example.foodplaner.models.Meal;

import io.reactivex.rxjava3.disposables.Disposable;

public interface HomePresenter {
    Disposable getDayMeal();
    Disposable getCategories();
    Disposable getSuggested();
    Disposable addDayToFav(Meal meal);
    Disposable removeDayFromFav(Meal meal);
    Disposable showDayFav(String id);
}
