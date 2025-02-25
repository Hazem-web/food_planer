package com.example.foodplaner.favorites.views;

import com.example.foodplaner.models.Meal;

import java.util.List;

public interface FavoritesView {
    void showData(List<Meal> meals);
    void mealRemoved();
    void showError(String error);
}
