package com.example.foodplaner.favorites.views;

import com.example.foodplaner.models.Meal;

public interface FavoritesHandler {
    void viewRecipe(String id);

    void removeFromFav(Meal meal);
}
