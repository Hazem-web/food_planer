package com.example.foodplaner.calendar.presenter;

import com.example.foodplaner.calendar.views.CalendarView;

import com.example.foodplaner.models.MealPlanned;
import com.example.foodplaner.models.repository.Repository;

import java.util.Date;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlannedPresenterImp implements PlannedPresenter{
    private CalendarView view;
    private Repository repository;

    public PlannedPresenterImp(CalendarView view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public Disposable getPlanned(Date date) {
        return repository.getPlannedMealsByDate(date).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(mealPlanneds -> {
                    view.showData(mealPlanneds);
                },throwable -> {
                    throwable.printStackTrace();
                    view.showError(throwable.getMessage());
                });
    }

    @Override
    public Disposable removeFromPlanned(MealPlanned mealPlanned) {
        return repository.deletePlannedMeal(mealPlanned).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(() -> {
                    view.mealRemoved();
                },throwable -> {
                    throwable.printStackTrace();
                    view.showError(throwable.getMessage());
                });
    }
}
