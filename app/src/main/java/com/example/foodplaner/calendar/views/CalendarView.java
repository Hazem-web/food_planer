package com.example.foodplaner.calendar.views;

import com.example.foodplaner.models.Meal;
import com.example.foodplaner.models.MealPlanned;

import java.util.List;

public interface CalendarView {
    void showData(List<MealPlanned> meals);
    void mealRemoved();
    void showError(String error);
}
