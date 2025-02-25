package com.example.foodplaner.searchresult.views;

public interface SearchResultHandler {

    void viewRecipe(String id);

    void notAuthorizedUser(String msg);

    void handleError(String error);
}
