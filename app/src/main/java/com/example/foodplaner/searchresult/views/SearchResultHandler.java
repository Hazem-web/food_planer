package com.example.foodplaner.searchresult.views;

public interface SearchResultHandler {

    void viewRecipe(String id);

    void notAuthorizedUser();

    void handleError(String error);
}
