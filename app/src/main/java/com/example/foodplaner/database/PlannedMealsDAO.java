package com.example.foodplaner.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.foodplaner.models.Meal;
import com.example.foodplaner.models.MealPlanned;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface PlannedMealsDAO {
    @Insert
    Completable insertPlanned(MealPlanned mealPlanned);

    @Delete
    Completable deletePlanned(MealPlanned mealPlanned);

    @Query("SELECT * FROM PLANNED_MEALS WHERE date=:date")
    Flowable<List<MealPlanned>> getPlannedMealsByDay(Date date);

    @Query("SELECT * FROM PLANNED_MEALS WHERE id= :id")
    Single<MealPlanned> getPlannedMealById(String id);
}
