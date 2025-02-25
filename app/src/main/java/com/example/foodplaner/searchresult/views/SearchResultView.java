package com.example.foodplaner.searchresult.views;

import com.example.foodplaner.models.Meal;

import java.util.List;

public interface SearchResultView {
    void showData(List<Meal> meals);
    void showError(String error);
}
