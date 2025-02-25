package com.example.foodplaner.searchresult.presenter;


import com.example.foodplaner.models.repository.Repository;
import com.example.foodplaner.searchresult.views.SearchResultView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchResultPresenterImp implements SearchResultPresenter{

    private SearchResultView view;
    private Repository repository;

    public SearchResultPresenterImp(SearchResultView view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }
    @Override
    public Disposable getData(String type, String name) {
        Disposable output;
        if (type.equals("Area")){
            output=repository.getMealsByArea(name).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mealResponse -> {
                        view.showData(mealResponse.getMeals());
                    },throwable -> {
                        throwable.printStackTrace();
                        view.showError(throwable.getMessage());
                    });
        }
        else if (type.equals("Category")){
            output=repository.getCategoryMeals(name).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mealResponse -> {
                        view.showData(mealResponse.getMeals());
                    },throwable -> {
                        throwable.printStackTrace();
                        view.showError(throwable.getMessage());
                    });
        }
        else {
            output=repository.getMealsByIngredient(name).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mealResponse -> {
                        view.showData(mealResponse.getMeals());
                    },throwable -> {
                        throwable.printStackTrace();
                        view.showError(throwable.getMessage());
                    });
        }
        return output;
    }
}
