package com.example.foodplaner.models;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class Meal implements Displayable{
    @SerializedName("idMeal")
    private String id;

    @SerializedName("strMeal")
    private String name;

    @SerializedName("strCategory")
    private String category;

    @SerializedName("strArea")
    private String area;

    @SerializedName("strInstructions")
    private String instructions;

    @SerializedName("strMealThumb")
    private String img;

    @SerializedName("strTags")
    private String tags;

    @SerializedName("strYoutube")
    private String video;

    @SerializedName("strSource")
    private String source;

    private String ingredients;
    private String measures;

    // Method to dynamically set ingredients and measures
    public void extractIngredientsAndMeasures() {
        for (int i = 1; i <= 20; i++) {
            try {
                String ingredient = (String) this.getClass().getDeclaredField("strIngredient" + i).get(this);
                String measure = (String) this.getClass().getDeclaredField("strMeasure" + i).get(this);

                if (ingredient != null && !ingredient.isEmpty()) {
                    ingredients+=ingredient;
                    measures+=(measure != null ? measure : "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getArea() {
        return area;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getImg() {
        return img;
    }

    @Override
    public String getObjectType() {
        return "Meal";
    }

    public String getTags() {
        return tags;
    }

    public String getVideo() {
        return video;
    }

    public String getSource() {
        return source;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getMeasures() {
        return measures;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setMeasures(String measures) {
        this.measures = measures;
    }
}

