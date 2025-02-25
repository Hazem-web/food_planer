package com.example.foodplaner.favorites.presenter;

import com.example.foodplaner.models.Meal;

import io.reactivex.rxjava3.disposables.Disposable;

public interface FavoritesPresenter {
    Disposable getFav();

    Disposable removeFav(Meal meal);
}
