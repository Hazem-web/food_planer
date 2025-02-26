package com.example.foodplaner.localmeal.presenter;

import com.example.foodplaner.localmeal.views.LocalMealView;
import com.example.foodplaner.models.Meal;
import com.example.foodplaner.models.MealPlanned;
import com.example.foodplaner.models.repository.Repository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LocalMealPresenterImp implements LocalMealPresenter {
    private LocalMealView view;
    private Repository repository;

    public LocalMealPresenterImp(LocalMealView view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public Disposable showData(String id,String type) {
        Disposable output;
        if (type.equals("planned")){
            output=repository.getPlannedMealById(id).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mealResponse -> {
                        view.showData(new Meal(mealResponse.get(0)));
                    },throwable -> {
                        throwable.printStackTrace();
                    });
        }
        else {
            output=repository.getFavMealById(id).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mealResponse -> {
                        view.showData((mealResponse));
                    },throwable -> {
                        throwable.printStackTrace();
                    });
        }
        return output;
    }

    @Override
    public Disposable addToFav(Meal meal) {
        return repository.insertFavMeal(meal).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    view.showFav();
                },throwable -> {
                });
    }

    @Override
    public Disposable removeFromFav(Meal meal) {
        return repository.deleteFavMeal(meal).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    view.showNotFav();
                },throwable -> {
                });
    }

    @Override
    public Disposable addToPlanned(MealPlanned mealPlanned) {
        return repository.insertPlannedMeal(mealPlanned).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    view.addToPlanned();
                },throwable -> {
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

                });
    }
}
