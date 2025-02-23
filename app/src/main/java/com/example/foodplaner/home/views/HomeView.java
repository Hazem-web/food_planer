package com.example.foodplaner.home.views;

import com.example.foodplaner.models.Category;
import com.example.foodplaner.models.Meal;

import java.util.List;

public interface HomeView {
    void showDailyMeal(Meal meal);
    void showSuggestedMeals(List<Meal> meals);
    void showFavDaily();
    void showNotFavDaily();
    void showCategories(List<Category> categories);
    void showError(String error);
}
