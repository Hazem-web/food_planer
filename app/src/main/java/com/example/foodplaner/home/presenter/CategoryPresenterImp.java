package com.example.foodplaner.home.presenter;

import android.util.Log;

import com.example.foodplaner.home.views.CategoryView;
import com.example.foodplaner.models.repository.Repository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryPresenterImp implements CategoryPresenter{
    private CategoryView view;
    private Repository repository;

    public CategoryPresenterImp(CategoryView view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public Disposable getData(String cat,int position) {
        return repository.getCategoryMeals(cat).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealResponse -> {
                    view.showData(mealResponse.getMeals(),position);
                },throwable -> {
                    throwable.printStackTrace();
                    view.showError(throwable.getMessage());
                });
    }
}
