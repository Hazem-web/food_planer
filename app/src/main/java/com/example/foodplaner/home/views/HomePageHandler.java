package com.example.foodplaner.home.views;

public interface HomePageHandler {
    void viewRecipe(String id);
    void notAuthor();
    void showCategory(String name);
    void handleError(String error);
}
