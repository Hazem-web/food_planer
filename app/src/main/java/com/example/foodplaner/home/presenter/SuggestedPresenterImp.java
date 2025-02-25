package com.example.foodplaner.home.presenter;

import com.example.foodplaner.home.views.SuggestedView;
import com.example.foodplaner.models.Meal;
import com.example.foodplaner.models.repository.Repository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SuggestedPresenterImp implements SuggestedPresenter{
    private SuggestedView suggestedView;
    private Repository repository;

    public SuggestedPresenterImp(SuggestedView suggestedView, Repository repository) {
        this.suggestedView = suggestedView;
        this.repository = repository;
    }

    @Override
    public Disposable addToFav(Meal meal,int position) {
        return repository.insertFavMeal(meal).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(() -> {
                    suggestedView.showFav(position);
                },throwable -> {
                    throwable.printStackTrace();
                });
    }

    @Override
    public Disposable removeFromFav(Meal meal,int position) {
        return repository.deleteFavMeal(meal).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(() -> {
                    suggestedView.showNotFav(position);
                },throwable -> {
                    throwable.printStackTrace();
                });
    }

    @Override
    public Disposable showFav(String id,int position) {
        return repository.getFavMealById(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meal -> {
                    if (meal==null){
                        suggestedView.showNotFav(position);
                    }
                    else {
                        suggestedView.showFav(position);
                    }
                },throwable -> {
                    throwable.printStackTrace();
                });
    }
}
