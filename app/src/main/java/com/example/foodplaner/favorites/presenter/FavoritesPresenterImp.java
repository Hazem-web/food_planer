package com.example.foodplaner.favorites.presenter;

import android.util.Log;

import com.example.foodplaner.favorites.views.FavoritesView;
import com.example.foodplaner.models.Meal;
import com.example.foodplaner.models.repository.Repository;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoritesPresenterImp implements FavoritesPresenter{
    private FavoritesView view;
    private Repository repository;

    public FavoritesPresenterImp(FavoritesView view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public Disposable getFav() {
        return repository.getFavMeals().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(meals -> {
                    view.showData(meals);
                },throwable -> {
                    throwable.printStackTrace();
                    view.showError(throwable.getMessage());
                });
    }

    @Override
    public Disposable removeFav(Meal meal) {
        return repository.deleteFavMeal(meal).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(() -> {
                    view.mealRemoved();
                },throwable -> {
                    throwable.printStackTrace();
                    view.showError(throwable.getMessage());
                });
    }
}
