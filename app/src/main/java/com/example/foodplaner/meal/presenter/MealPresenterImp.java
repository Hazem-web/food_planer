package com.example.foodplaner.meal.presenter;

import android.util.Log;

import com.example.foodplaner.meal.views.MealView;
import com.example.foodplaner.models.Meal;
import com.example.foodplaner.models.MealPlanned;
import com.example.foodplaner.models.repository.Repository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealPresenterImp implements MealPresenter{
    private MealView view;
    private Repository repository;

    public MealPresenterImp(MealView view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public Disposable showData(String id) {
        return repository.getSelectedMeal(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealResponse -> {
                    view.showData(mealResponse.getMeals().get(0));
                },throwable -> {
                    throwable.printStackTrace();
                    view.showError(throwable.getMessage());
                });
    }

    @Override
    public Disposable addToFav(Meal meal) {
        return repository.insertFavMeal(meal).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    view.showFav();
                },throwable -> {
                    view.showError(throwable.getMessage());
                });
    }

    @Override
    public Disposable removeFromFav(Meal meal) {
        return repository.deleteFavMeal(meal).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    view.showNotFav();
                },throwable -> {
                    view.showError(throwable.getMessage());
                });
    }

    @Override
    public Disposable addToPlanned(MealPlanned mealPlanned) {
        return repository.insertPlannedMeal(mealPlanned).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    view.addToPlanned();
                },throwable -> {
                    view.showError(throwable.getMessage());
                });
    }

    @Override
    public Disposable showFav(String id) {
        return repository.getFavMealById(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meal -> {
                    if (meal != null) {
                        view.showFav();
                    }else {
                        view.showNotFav();
                    }
                },throwable -> {

                    view.showError(throwable.getMessage());
                });
    }
}
