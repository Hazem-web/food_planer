package com.example.foodplaner.models;

public class IngredientMeal {
    private static final String URL="https://www.themealdb.com/images/ingredients/";
    private String name;
    private String measure;

    public IngredientMeal(String name, String measure) {
        this.name = name;
        this.measure = measure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getImg(){
        return URL+name+".png";
    }
}
