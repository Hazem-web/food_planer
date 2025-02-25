package com.example.foodplaner.searchresult.presenter;

import com.example.foodplaner.models.Meal;

import io.reactivex.rxjava3.disposables.Disposable;

public interface MealsPresenter {
    Disposable addToFav(Meal meal,int position);
    Disposable removeFromFav(Meal meal,int position);
    Disposable showFav(String id,int position);
}
