package com.example.foodplaner.home.views;

import com.example.foodplaner.models.Meal;

import java.util.List;

public interface CategoryView {
    void showData(List<Meal> meals,int position);
    void showError(String error);
}
