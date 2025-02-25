package com.example.foodplaner.calendar.views;

import com.example.foodplaner.models.MealPlanned;

public interface CalendarHandler {
    void viewRecipe(String id);

    void removeFromPlanned(MealPlanned planned);
}
