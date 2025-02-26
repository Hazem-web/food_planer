package com.example.foodplaner.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplaner.models.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MealsFavDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertFav(Meal meal);

    @Delete
    Completable deleteFav(Meal meal);

    @Query("SELECT * FROM FAV_MEAL")
    Flowable<List<Meal>> getFavMeals();

    @Query("SELECT * FROM FAV_MEAL WHERE id= :id")
    Single<Meal> getFavMealById(String id);
}
