package com.example.foodplaner.search.views;

import com.example.foodplaner.models.Displayable;

import java.util.List;

public interface SearchView {
    void showData(List<Displayable> data);

    void showError(String error);
}
