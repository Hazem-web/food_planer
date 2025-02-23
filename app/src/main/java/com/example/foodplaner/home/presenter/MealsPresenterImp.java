package com.example.foodplaner.home.presenter;

import android.util.Log;

import com.example.foodplaner.home.views.MealView;
import com.example.foodplaner.models.Meal;
import com.example.foodplaner.models.repository.Repository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsPresenterImp implements MealsPresenter{
    private MealView view;
    private Repository repository;

    public MealsPresenterImp(MealView view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }
    @Override
    public Disposable addToFav(Meal meal, int position) {
        return repository.getSelectedMeal(meal.getId()).subscribeOn(Schedulers.io())
                .subscribe(mealResponse -> {
                    Log.i("nonono", "letsgo");
                    if (mealResponse.getMeals() != null) {
                        repository.insertFavMeal(mealResponse.getMeals().get(0)).subscribeOn(Schedulers.io())
                               .observeOn(AndroidSchedulers.mainThread())
                               .subscribe(() -> {
                                   view.showFav(position);
                               },throwable -> {

                               });
                    }
                },throwable -> {
                    throwable.printStackTrace();

                });
    }

    @Override
    public Disposable removeFromFav(Meal meal, int position) {
        return repository.deleteFavMeal(meal).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(() -> {
                    view.showNotFav(position);
                },throwable -> {});
    }

    @Override
    public Disposable showFav(String id, int position) {
        return repository.getFavMealById(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meal -> {
                    if (meal==null){
                        view.showNotFav(position);
                    }
                    else {
                        view.showFav(position);
                    }
                },throwable -> {

                });
    }
}
