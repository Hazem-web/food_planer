package com.example.foodplaner.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class IngredientResponse {
    @SerializedName("meals")
    private List<Ingredient> ingredients;

    // Getter
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    // Setter
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
