package com.example.foodplaner.database;

import android.content.Context;

import com.example.foodplaner.models.Meal;
import com.example.foodplaner.models.MealPlanned;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class MealsLocalDataSourceImp implements MealsLocalDataSource{
    private MealsFavDAO mealsFavDAO;
    private PlannedMealsDAO plannedMealsDAO;
    private static MealsLocalDataSourceImp client=null;

    private MealsLocalDataSourceImp(Context context){
        AppDataBase dataBase=AppDataBase.getInstance(context);
        mealsFavDAO=dataBase.getMealsFavDAO();
        plannedMealsDAO=dataBase.getPlannedMealsDAO();
    }

    public static MealsLocalDataSourceImp getInstance(Context context){
        if (client == null) {
            client=new MealsLocalDataSourceImp(context);
        }
        return client;
    }
    @Override
    public Completable insertFav(Meal meal) {
        return mealsFavDAO.insertFav(meal);
    }

    @Override
    public Completable deleteFav(Meal meal) {
        return mealsFavDAO.deleteFav(meal);
    }

    @Override
    public Flowable<List<Meal>> getFavMeals() {
        return mealsFavDAO.getFavMeals();
    }

    @Override
    public Single<Meal> getFavMealById(String id) {
        return mealsFavDAO.getFavMealById(id);
    }

    @Override
    public Completable insertPlanned(MealPlanned mealPlanned) {
        return plannedMealsDAO.insertPlanned(mealPlanned);
    }

    @Override
    public Completable deletePlanned(MealPlanned mealPlanned) {
        return plannedMealsDAO.deletePlanned(mealPlanned);
    }

    @Override
    public Flowable<List<MealPlanned>> getPlannedMealsByDay(Date date) {
        return plannedMealsDAO.getPlannedMealsByDay(date);
    }
}
