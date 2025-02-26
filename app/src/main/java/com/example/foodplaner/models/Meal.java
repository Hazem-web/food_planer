package com.example.foodplaner.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "FAV_MEAL")
public class Meal implements Displayable{
    public Meal(@NonNull String id, String name, String category, String area, String instructions, String img, String tags, String video, String source, String ingredients, String measures, boolean isFav) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.area = area;
        this.instructions = instructions;
        this.img = img;
        this.tags = tags;
        this.video = video;
        this.source = source;
        this.ingredients = ingredients;
        this.measures = measures;
        this.isFav = isFav;
    }

    public Meal() {
    }

    public Meal(MealPlanned mealPlanned){
        id=mealPlanned.getId();
        name=mealPlanned.getName();
        category=mealPlanned.getCategory();
        area=mealPlanned.getArea();
        instructions=mealPlanned.getInstructions();
        img=mealPlanned.getImg();
        tags=mealPlanned.getTags();
        video=mealPlanned.getVideo();
        ingredients=mealPlanned.getIngredients();
        measures=mealPlanned.getMeasures();
        source=mealPlanned.getSource();
    }

    @PrimaryKey
    @NonNull
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

    @Ignore
    private boolean isFav=false;

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

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }
}

