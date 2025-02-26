package com.example.foodplaner.authentication.profile.persenter;

import io.reactivex.rxjava3.disposables.Disposable;

public interface ProfilePresenter {
    Disposable backupPlanned();
    Disposable backupFav();
    void syncPlanned();
    void syncFav();
}
