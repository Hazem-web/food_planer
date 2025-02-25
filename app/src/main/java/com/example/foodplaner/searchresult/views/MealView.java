package com.example.foodplaner.searchresult.views;

public interface MealView {

    void showFav(int position);

    void showNotFav(int position);

    void showError(String msg);}
