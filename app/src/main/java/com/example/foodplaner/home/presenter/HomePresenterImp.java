package com.example.foodplaner.home.presenter;

import android.util.Log;

import com.example.foodplaner.home.views.HomeView;
import com.example.foodplaner.models.Meal;
import com.example.foodplaner.models.repository.Repository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenterImp implements HomePresenter{
    private HomeView homeView;
    private Repository repository;

    public HomePresenterImp(HomeView homeView, Repository repository) {
        this.homeView = homeView;
        this.repository = repository;
    }

    @Override
    public Disposable getDayMeal() {
        return repository.getMealOfTheDay().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealResponse -> {
                    homeView.showDailyMeal(mealResponse.getMeals().get(0));
                },throwable -> {
                    homeView.showError(throwable.getMessage());
                });
    }

    @Override
    public Disposable getCategories() {
        return repository.getCategories().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categoryFilterResponse -> {
                    homeView.showCategories(categoryFilterResponse.getCategories());
                },throwable -> {
                    homeView.showError(throwable.getMessage());
                });
    }

    @Override
    public Disposable getSuggested() {
        return repository.getSuggestedMeals().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealResponse -> {
                    if (mealResponse == null) {
                        homeView.showError("");
                    }
                    else {
                        homeView.showSuggestedMeals(mealResponse.getMeals());
                    }
                }, throwable -> {
                    homeView.showError(throwable.getMessage());
                });
    }

    @Override
    public Disposable addDayToFav(Meal meal) {
        return repository.insertFavMeal(meal).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                        homeView.showFavDaily();
                },throwable -> {
                    throwable.printStackTrace();
                });
    }

    @Override
    public Disposable removeDayFromFav(Meal meal) {
        return repository.deleteFavMeal(meal).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    homeView.showNotFavDaily();
                },throwable -> {
                    throwable.printStackTrace();
                });
    }

    @Override
    public Disposable showDayFav(String id) {
        return repository.getFavMealById(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meal -> {
                    if(meal==null){
                        homeView.showFavDaily();
                    }
                    else
                        homeView.showNotFavDaily();
                },throwable -> {
                    throwable.printStackTrace();
                });
    }
}
