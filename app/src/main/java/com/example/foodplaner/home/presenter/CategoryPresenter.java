package com.example.foodplaner.home.presenter;

import com.example.foodplaner.models.Meal;

import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;

public interface CategoryPresenter {
    Disposable getData(String cat,int position);
}
