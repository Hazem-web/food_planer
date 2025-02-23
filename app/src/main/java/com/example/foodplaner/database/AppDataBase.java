package com.example.foodplaner.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.foodplaner.models.Converters;
import com.example.foodplaner.models.Meal;
import com.example.foodplaner.models.MealPlanned;

@Database(entities = {Meal.class, MealPlanned.class},version = 1)
@TypeConverters({Converters.class})
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase instance=null;
    public abstract MealsFavDAO getMealsFavDAO();
    public abstract PlannedMealsDAO getPlannedMealsDAO();
    public static synchronized AppDataBase getInstance(Context context){
        if (instance == null) {
            instance= Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class,"mealsDB").build();
        }
        return instance;
    }
}
