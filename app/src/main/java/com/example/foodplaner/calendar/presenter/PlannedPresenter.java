package com.example.foodplaner.calendar.presenter;

import com.example.foodplaner.models.Meal;
import com.example.foodplaner.models.MealPlanned;

import java.util.Date;

import io.reactivex.rxjava3.disposables.Disposable;

public interface PlannedPresenter {
    Disposable getPlanned(Date date);

    Disposable removeFromPlanned(MealPlanned mealPlanned);

}
