package com.example.foodplaner.network;

import com.example.foodplaner.models.AreaResponse;
import com.example.foodplaner.models.CategoryFilterResponse;
import com.example.foodplaner.models.CategoryResponse;
import com.example.foodplaner.models.IngredientResponse;
import com.example.foodplaner.models.Meal;
import com.example.foodplaner.models.MealDeserializer;
import com.example.foodplaner.models.MealResponse;
import com.example.foodplaner.network.services.FilterService;
import com.example.foodplaner.network.services.MealService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Random;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataSourceImp implements MealsRemoteDataSource{
    private static final String URL="https://www.themealdb.com//api/json/v1/1/";
    private static MealsRemoteDataSourceImp client=null;
    private final MealService mealService;
    private final FilterService filterService;

    private MealsRemoteDataSourceImp(){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Meal.class, new MealDeserializer()) // Custom deserializer
                .create();
        Retrofit retrofit=new Retrofit.Builder().baseUrl(URL).
                addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build();
        mealService=retrofit.create(MealService.class);
        filterService=retrofit.create(FilterService.class);
    }

    public static MealsRemoteDataSourceImp getInstance(){
        if (client == null) {
            client=new MealsRemoteDataSourceImp();
        }
        return client;
    }

    @Override
    public Single<MealResponse> getCategoryMeals(String cat) {
        return mealService.getMealsByCategory(cat);
    }

    @Override
    public Single<MealResponse> getMealOfTheDay() {
        return mealService.getMealOfTheDay();
    }

    @Override
    public Single<MealResponse> getSuggestedMeals() {
        Random random=new Random();
        return mealService.getMealsByFirstLetter(((char)('a' + random.nextInt(26)))+"");
    }

    @Override
    public Single<CategoryResponse> getCategories() {
        return filterService.getFilterCategories();
    }

    @Override
    public Single<AreaResponse> getAreas() {
        return filterService.getFilterAreas();
    }

    @Override
    public Single<IngredientResponse> getIngredients() {
        return filterService.getFilterIngredients();
    }

    @Override
    public Single<MealResponse> getSelectedMeal(String id) {
        return mealService.getMealById(id);
    }

    @Override
    public Single<MealResponse> getMealsByIngredient(String ing) {
        return mealService.getMealsByIngredient(ing);
    }

    @Override
    public Single<MealResponse> getMealsByArea(String area) {
        return mealService.getMealsByArea(area);
    }

    @Override
    public Single<MealResponse> searchMeal(String name) {
        return mealService.getMealsByName(name);
    }
}
