package com.example.foodplaner.models;

import com.google.gson.annotations.SerializedName;

public class Ingredient implements Displayable{
    private static final String URL="www.themealdb.com/images/ingredients/";
    @SerializedName("idIngredient")
    private String id;

    @SerializedName("strIngredient")
    private String name;

    @SerializedName("strDescription")
    private String description;

    @SerializedName("strType")
    private String type;

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getImg() {
        return URL+name+".png";
    }

    @Override
    public String getObjectType() {
        return "Ingredient";
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }
}