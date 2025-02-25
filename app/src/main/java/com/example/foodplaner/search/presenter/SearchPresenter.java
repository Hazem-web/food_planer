package com.example.foodplaner.search.presenter;

import io.reactivex.rxjava3.disposables.Disposable;

public interface SearchPresenter {
    Disposable getAllOf(String type);

    Disposable getMealByName(String name);

}
