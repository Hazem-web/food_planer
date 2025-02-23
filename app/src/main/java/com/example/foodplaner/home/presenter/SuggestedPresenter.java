package com.example.foodplaner.home.presenter;

import com.example.foodplaner.models.Meal;

import io.reactivex.rxjava3.disposables.Disposable;

public interface SuggestedPresenter {
    Disposable addToFav(Meal meal,int position);
    Disposable removeFromFav(Meal meal,int position);
    Disposable showFav(String id,int position);
}
