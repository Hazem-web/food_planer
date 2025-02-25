package com.example.foodplaner.models.repository;

import com.example.foodplaner.database.MealsLocalDataSource;
import com.example.foodplaner.models.AreaResponse;
import com.example.foodplaner.models.CategoryFilterResponse;
import com.example.foodplaner.models.CategoryResponse;
import com.example.foodplaner.models.IngredientResponse;
import com.example.foodplaner.models.Meal;
import com.example.foodplaner.models.MealPlanned;
import com.example.foodplaner.models.MealResponse;
import com.example.foodplaner.network.MealsRemoteDataSource;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class RepositoryImp implements Repository{

    private MealsRemoteDataSource remoteDataSource;
    private MealsLocalDataSource localDataSource;

    private static RepositoryImp repo=null;

    private RepositoryImp(MealsRemoteDataSource remoteDataSource, MealsLocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    public static RepositoryImp getInstance(MealsRemoteDataSource remoteDataSource, MealsLocalDataSource localDataSource){
        if (repo == null) {
            repo=new RepositoryImp(remoteDataSource,localDataSource);
        }
        return repo;
    }

    @Override
    public Single<MealResponse> getCategoryMeals(String cat) {
        return remoteDataSource.getCategoryMeals(cat);
    }

    @Override
    public Single<MealResponse> getMealOfTheDay() {
        return remoteDataSource.getMealOfTheDay();
    }

    @Override
    public Single<MealResponse> getSuggestedMeals() {
        return remoteDataSource.getSuggestedMeals();
    }

    @Override
    public Single<CategoryResponse> getCategories() {
        return remoteDataSource.getCategories();
    }

    @Override
    public Single<AreaResponse> getAreas() {
        return remoteDataSource.getAreas();
    }

    @Override
    public Single<IngredientResponse> getIngredients() {
        return remoteDataSource.getIngredients();
    }

    @Override
    public Single<MealResponse> getSelectedMeal(String id) {
        return remoteDataSource.getSelectedMeal(id);
    }

    @Override
    public Single<MealResponse> getMealsByIngredient(String ing) {
        return remoteDataSource.getMealsByIngredient(ing);
    }

    @Override
    public Single<MealResponse> getMealsByArea(String area) {
        return remoteDataSource.getMealsByArea(area);
    }

    @Override
    public Single<MealResponse> searchMeal(String name) {
        return remoteDataSource.searchMeal(name);
    }

    @Override
    public Flowable<List<Meal>> getFavMeals() {
        return localDataSource.getFavMeals();
    }

    @Override
    public Single<Meal> getFavMealById(String id) {
        return localDataSource.getFavMealById(id);
    }

    @Override
    public Completable insertFavMeal(Meal meal) {
        return localDataSource.insertFav(meal);
    }

    @Override
    public Completable deleteFavMeal(Meal meal) {
        return localDataSource.deleteFav(meal);
    }

    @Override
    public Flowable<List<MealPlanned>> getPlannedMealsByDate(Date date) {
        return localDataSource.getPlannedMealsByDay(date);
    }

    @Override
    public Single<MealPlanned> getPlannedMealById(String id) {
        return localDataSource.getPlannedMealById(id);
    }

    @Override
    public Completable insertPlannedMeal(MealPlanned meal) {
        return localDataSource.insertPlanned(meal);
    }

    @Override
    public Completable deletePlannedMeal(MealPlanned meal) {
        return localDataSource.deletePlanned(meal);
    }
}
