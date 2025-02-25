package com.example.foodplaner.localmeal.views;

import com.example.foodplaner.models.Meal;

public interface LocalMealView {
    void showFav();

    void showNotFav();

    void showData(Meal meal);

    void addToPlanned();

    void showError(String error);
}
