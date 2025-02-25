package com.example.foodplaner.searchresult.presenter;

import io.reactivex.rxjava3.disposables.Disposable;

public interface SearchResultPresenter {
    Disposable getData(String type, String name);
}
