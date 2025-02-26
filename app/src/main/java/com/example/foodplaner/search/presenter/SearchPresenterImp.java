package com.example.foodplaner.search.presenter;

import com.example.foodplaner.models.Area;
import com.example.foodplaner.models.Displayable;
import com.example.foodplaner.models.repository.Repository;
import com.example.foodplaner.search.views.SearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImp implements SearchPresenter{
    private SearchView view;
    private Repository repository;

    public SearchPresenterImp(SearchView view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public Disposable getAllOf(String type) {
        Disposable output;
        if (type.equals("Area")){
            output=repository.getAreas().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(areaResponse -> {
                        List<Displayable> displayable=areaResponse.getAreas().stream().map(area -> (Displayable) area).collect(Collectors.toList());
                        view.showData(displayable);
                    },throwable -> {
                        view.showError(throwable.getMessage());
                    });
        }
        else if (type.equals("Category")){
            output=repository.getCategories().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(categoryResponse -> {
                        List<Displayable> displayable=categoryResponse.getCategories().stream().map(category -> (Displayable) category).collect(Collectors.toList());
                        view.showData(displayable);
                    },throwable -> {
                        view.showError(throwable.getMessage());
                    });
        }
        else {
            output=repository.getIngredients().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(ingredientResponse -> {
                        List<Displayable> displayable=ingredientResponse.getIngredients().stream().map(ingredient -> (Displayable) ingredient).collect(Collectors.toList());
                        view.showData(displayable);
                    },throwable -> {
                        view.showError(throwable.getMessage());
                    });
        }
        return output;
    }

    @Override
    public Disposable getMealByName(String name) {
        return repository.searchMeal(name).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealResponse -> {
                    List<Displayable> displayable=mealResponse.getMeals().stream().map(meal -> (Displayable) meal).collect(Collectors.toList());
                    view.showData(displayable);
                },throwable -> {
                    view.showError(throwable.getMessage());
                });
    }
}
