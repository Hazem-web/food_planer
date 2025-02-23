package com.example.foodplaner.meal.views;

import com.example.foodplaner.models.Meal;

public interface MealView {
    void showFav();

    void showNotFav();

    void showData(Meal meal);

    void addToPlanned();

    void showError(String error);
}
